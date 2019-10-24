package javax0.license3j;

import javax0.license3j.crypto.LicenseKeyPair;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Modifier;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.security.*;
import java.util.*;

/**
 * A license describes the rights that a certain user has. The rights are represented by {@link Feature}s.
 * Each feature has a name, type and a value. The license is essentially the set of features.
 * <p>
 * As examples features can be license expiration date and time, number of users allowed to use the software,
 * name of rights and so on.
 */
public class License {
    private static final int MAGIC = 0x21CE_4E_5E; // LICE(N=4E)SE
    final private static String LICENSE_ID = "licenseId";
    private static final String SIGNATURE_KEY = "licenseSignature";
    private static final String DIGEST_KEY = "signatureDigest";
    final private static String EXPIRATION_DATE = "expiryDate";
    final private Map<String, Feature> features = new HashMap<>();

    public License() {
    }

    protected License(License license) {
        features.putAll(license.features);
    }

    /**
     * Get a feature of a given name from the license or {@code null} if there is no feature for the name in the
     * license.
     *
     * @param name the name of the feature we want to retrieve
     * @return the feature object
     */
    public Feature get(String name) {
        return features.get(name);
    }


    /**
     * Checks the expiration date of the license and returns {@code true} if the license has expired.
     * <p>
     * The expiration date is stored in the license feature {@code expiryDate}. A license is expired
     * if the current date is after the specified {@code expiryDate}. At the given date (ms precision) the
     * license is still valid.
     * <p>
     * The method does not check that the license is properly signed or not. That has to be checked using a
     * separate call to the underlying license.
     *
     * @return {@code true} if the license has expired.
     */
    public boolean isExpired() {
        final var expiryDate = get(EXPIRATION_DATE).getDate();
        final var today = new Date();
        return today.getTime() > expiryDate.getTime();
    }

    /**
     * Set the expiration date of the license.
     *
     * @param expiryDate the date when the license expires
     */
    public void setExpiry(final Date expiryDate) {
        add(Feature.Create.dateFeature(EXPIRATION_DATE, expiryDate));
    }
    /**
     * Sign the license.
     * <p>
     * The license is signed the following way:
     * <ol>
     * <li>Add the digest algorithm string to the license as a feature. The feature name is {@code signatureDigest}
     * (name is defined in the constant {@link #DIGEST_KEY} in this class).
     * </li>
     * <li>The license is converted to binary format</li>
     * <li>A digest is created from the binary license using the message digest algorithm named by the {@code digest}
     * parameter</li>
     * <li>The digest is encrypted using the key (which also has the information about the algorithm).</li>
     * <li>The encrypted digest is added to the license as a new {@code BINARY} feature as signature.</li>
     * </ol>
     *
     * @param key    the private key to be used to create the signature
     * @param digest the name of the digest algorithm
     * @throws NoSuchAlgorithmException  this exception comes from the underlying encryption library
     * @throws NoSuchPaddingException    this exception comes from the underlying encryption library
     * @throws InvalidKeyException       this exception comes from the underlying encryption library
     * @throws BadPaddingException       this exception comes from the underlying encryption library
     * @throws IllegalBlockSizeException this exception comes from the underlying encryption library
     */
    public void sign(PrivateKey key, String digest) throws NoSuchAlgorithmException, NoSuchPaddingException,
        InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        add(Feature.Create.stringFeature(DIGEST_KEY, digest));
        final var digester = MessageDigest.getInstance(digest);
        final var ser = unsigned();
        final var digestValue = digester.digest(ser);
        final var cipher = Cipher.getInstance(key.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, key);
        final var signature = cipher.doFinal(digestValue);
        add(signature);
    }

    /**
     * See {@link #isOK(PublicKey)}.
     *
     * @param key serialized encryption key to check the authenticity of the license signature
     * @return see {@link #isOK(PublicKey)}
     */
    public boolean isOK(byte[] key) {
        try {
            return isOK(LicenseKeyPair.Create.from(key, Modifier.PUBLIC).getPair().getPublic());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns true if the license is signed and the authenticity of the signature can be checked successfully
     * using the key.
     *
     * @param key encryption key to check the authenticity of the license signature
     * @return {@code true} if the license was properly signed and is intact. In any other cases it returns
     * {@code false}.
     */
    public boolean isOK(PublicKey key) {
        try {
            final var digester = MessageDigest.getInstance(get(DIGEST_KEY).getString());
            final var ser = unsigned();
            final var digestValue = digester.digest(ser);
            final var cipher = Cipher.getInstance(key.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, key);
            final var sigDigest = cipher.doFinal(getSignature());
            return Arrays.equals(digestValue, sigDigest);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Add a feature to the license. Note that adding a feature to a license renders the license signature invalid.
     * Adding the feature does not remove the signature features though.
     * <p>
     * The method throws exception in case the feature is the license signature and the type is not {@code BINARY}.
     *
     * @param feature is added to the license
     * @return the previous feature of the same name but presumably different type and value or {@code null} in case
     * there was no previous feature in the license of the same name.
     */
    public Feature add(Feature feature) {
        if (feature.name().equals(SIGNATURE_KEY) && !feature.isBinary()) {
            throw new IllegalArgumentException("Signature of a license has to be binary.");
        }
        return features.put(feature.name(), feature);
    }

    /**
     * Converts a license to string. The string contains all the features and also the order of the
     * features are guaranteed to be always the same.
     * <p>
     * Every feature is formatted as
     * <pre>
     *     name:TYPE=value
     * </pre>
     * <p>
     * when the value is multiline then it is converted to be multiline. Multiline strings are represented as usually
     * in unix, with the HERE_STRING. The value in this case starts right after the {@code =} character with {@code <<}
     * characters and a string to the end of the line that does not appear as a single line inside the string value.
     * This value signals the end of the string and all other lines before it is part of the multiline string.
     * For example:
     *
     * <pre>
     *     feature name : STRING =&lt;&lt;END
     *   this is
     *     a multi-line
     *       string
     * END
     * </pre>
     * <p>
     * In this example the string {@code END} singals the end of the string and the lines between are part of the
     * strings. A feature string is also converted to multi-line representation if it happens to start with the
     * characters {@code &lt;&lt;}.
     * <p>
     * The generated string can be used as argument to {@link Create#from(String)}.
     *
     * @return the converted license as a string
     */
    @Override
    public String toString() {
        final var sb = new StringBuilder();
        Feature[] features = featuresSorted(Set.of());
        for (Feature feature : features) {
            final var valueString = feature.valueString();
            final String value =
                valueString.contains("\n") || valueString.startsWith("<<")
                    ? multilineValueString(valueString) : valueString;
            sb.append(feature.toStringWith(value)).append("\n");
        }
        return sb.toString();
    }

    /**
     * Get all the features in an array except the excluded ones in sorted order. The sorting is done on the name.
     *
     * @param excluded the set of the names of the features that are not included to the result
     * @return the array of the features sorted.
     */
    private Feature[] featuresSorted(Set<String> excluded) {
        return this.features.values().stream().filter(f -> !excluded.contains(f.name()))
            .sorted(Comparator.comparing(Feature::name)).toArray(Feature[]::new);
    }


    /**
     * Converts a possibly multiline string to a multiline representation of a feature value. For more information on
     * how multiline strings are stored as values see {@link #toString()}. The HERE_STRING value is calulated so that it
     * is as short as possible containing the characters {@code A} and {@code B} only so that it never appears in the
     * actual multi-line string.
     *
     * @param s the multiline string to be converted
     * @return the converted string with the leading {@code &lt;&lt;HERE_STRING} line and the terminating
     * {@code HERE_STRING} line.
     */
    private String multilineValueString(String s) {
        List<String> lines = new ArrayList<>(List.of(s.split("\n")));
        final var sb = new StringBuilder();
        var i = 0;
        for (final var line : lines) {
            sb.append(line.length() <= i || line.charAt(i) == 'A' ? 'B' : 'A');
            i++;
        }
        final var delimiter = sb.toString();
        String shortDelimiter = null;
        for (int j = 1; j < delimiter.length(); j++) {
            if (!lines.contains(delimiter.substring(0, j))) {
                shortDelimiter = delimiter.substring(0, j);
                break;
            }
        }
        lines.add(0, "<<" + shortDelimiter);
        lines.add(shortDelimiter);
        return String.join("\n", lines);
    }

    /**
     * Generates a new license id.
     * <p>
     * This ID is also stored in the license thus there is no need to create a feature and add it to the license.
     * <p>
     * Generating UUID can be handy when you want to identify each license individually. For example you want to store
     * revocation information about each license. The url to check the revocation may contain the
     * {@code $&#123;licenseId&#125;} place holder that will be replaced by the actual uuid stored in the license.
     *
     * @return the generated identifier.
     */
    public UUID setLicenseId() {
        final var uuid = UUID.randomUUID();
        setLicenseId(uuid);
        return uuid;
    }

    /**
     * Get the license identifier. The identifier of the license is a random UUID (128 bit random value) that can
     * optionally be set and can be used to identify the license. This Id can be used as a reference to the license
     * in databases, URLs. An example use it to upload a simple file to a publicly reachable server with the name
     * of the license UUID and that it can be retrieved via a URL containing the UUID. The licensed program downloads
     * the file (presumably zero length) and in case the response code is 200 OK then it assumes that the license is OK.
     * If the server is not reachable or for some other reason it cannot reach the file it may assume that this is a
     * technical glitch and go on working for a while, however if the response is a definitive 404 it means that the file
     * was removes that means the license was revoked.
     *
     * @return the UUID former identifier of the license or null in case the license does not contain an ID.
     */
    public UUID getLicenseId() {
        try {
            return get(LICENSE_ID).getUUID();
        } catch (final Exception e) {
            return null;
        }
    }

    /**
     * Get the fingerprint of a license. The fingerprint is a 128bit value calculated from the license itself using the
     * MD5 algorithm. The fingerprint is represented as a UUID. The fingerprint is never stored in the license as a
     * feature. If the fingerprint is stored in the license as a feature then the fingerprint of the license changes
     * so the stored fingerprint will not be the fingerprint of the license any more.
     * <p>
     * The calculation of the fingerprint ignores the signature of the license as well as the feature that stores the
     * name of the signature digest algorithm (usually "SHA-512"). This is to ensure that even if you change the
     * signature on the license using a different digest algorithm: fingerprint will not change. The fingerprint is
     * relevant to the core content of the license.
     * <p>
     * Since the fingerprint is calculated from the binary representation of the license using the MD5 algorithm
     * it is likely that each license will have different fingerprint. This fingerprint can be used to identify
     * licenses similarly like the license id (see {@link #setLicenseId(UUID)},{@link #getLicenseId()}). The
     * fingerprint can be used even when the license does not have random ID stored in the license.
     *
     * @return the calculated fingerprint of the license
     */
    public UUID fingerprint() {
        try {
            final var bb = ByteBuffer.wrap(MessageDigest.getInstance("MD5").digest(
                serialized(Set.of(SIGNATURE_KEY, DIGEST_KEY))));
            final var ms = bb.getLong();
            final var ls = bb.getLong();
            return new UUID(ms, ls);
        } catch (final Exception e) {
            return null;
        }
    }

    /**
     * Set the UUID of a license. Note that this UUID can be generated calling the method {@link #setLicenseId()},
     * which method automatically calls this method setting the generated UUID to be the identifier of the license.
     *
     * @param licenseId the uuid that was generated somewhere.
     */
    public void setLicenseId(final UUID licenseId) {
        add(Feature.Create.uuidFeature(LICENSE_ID, licenseId));
    }

    /**
     * Get the license serialized (not standard Java serialized!!!) as a byte array. This method is used to save the
     * license in {@code BINARY} format but not to sign the license. The returned byte array contains all the features
     * including the signature of the license.
     *
     * @return the license in binary format as a byte array
     */
    public byte[] serialized() {
        return serialized(Set.of());
    }

    /**
     * Get the license as a byte[] without the signature key. This byte array is used to create the signature of the
     * license. Obviously, the signature itself cannot be part of the signed part of the license.
     *
     * @return the byte array containing the license without the signature. Note that the message digest algorithm used
     * during the signature creation of the license and stored as a feature in the license is also signed.
     */
    public byte[] unsigned() {
        return serialized(Set.of(SIGNATURE_KEY));
    }

    /**
     * Add the signature to the license.
     *
     * @param signature the signature itself
     */
    public void add(byte[] signature) {
        add(Feature.Create.binaryFeature(SIGNATURE_KEY, signature));
    }

    /**
     * Get the signature of the license. The signature of a license is stored in the license as a {@code BINARY}
     * feature. This method retrieves the feature and then it retrieves the value of the feature and returns the raw
     * value.
     *
     * @return the electronic signature attached to the license
     */
    public byte[] getSignature() {
        return get(SIGNATURE_KEY).getBinary();
    }

    /**
     * Create a byte array representation of the license. Include all the features except those whose name is specified
     * in the {@code excluded} set.
     *
     * @param excluded set of the feature names that are not to be present in the byte array representation of the
     *                 license.
     * @return the byte array containing the license information except the excluded features. The byte array
     * creation is deterministic in the sense that the same license will always result the same byte array. The features
     * converted into binary and concatenated and their order is determined by primitive sorting.
     */
    private byte[] serialized(Set<String> excluded) {
        Feature[] includedFeatures = featuresSorted(excluded);
        final var featureNr = includedFeatures.length;
        byte[][] featuresSerialized = new byte[featureNr][];
        var i = 0;
        var size = 0;
        for (final var feature : includedFeatures) {
            featuresSerialized[i] = feature.serialized();
            size += featuresSerialized[i].length;
            i++;
        }

        final var buffer = ByteBuffer.allocate(size + Integer.BYTES * (featureNr + 1));
        buffer.putInt(MAGIC);
        for (final var featureSerialized : featuresSerialized) {
            buffer.putInt(featureSerialized.length);
            buffer.put(featureSerialized);
        }
        return buffer.array();
    }

    /**
     * Inner class containing factory methods to create a license object from various sources.
     */
    public static class Create {
        /**
         * Create a license from the binary byte array representation.
         *
         * @param array the binary byte array representation of the license
         * @return the license object
         */
        public static License from(final byte[] array) {
            if (array.length < Integer.BYTES) {
                throw new IllegalArgumentException("serialized license is too short");
            }
            final var license = new License();
            final var buffer = ByteBuffer.wrap(array);
            final var magic = buffer.getInt();
            if (magic != MAGIC) {
                throw new IllegalArgumentException("serialized license is corrupt");
            }
            while (buffer.hasRemaining()) {
                try {
                    final var featureLength = buffer.getInt();
                    final var featureSerialized = new byte[featureLength];
                    buffer.get(featureSerialized);
                    final var feature = Feature.Create.from(featureSerialized);
                    license.add(feature);
                } catch (BufferUnderflowException e) {
                    throw new IllegalArgumentException(e);
                }
            }
            return license;
        }

        /**
         * Get a license with the features from the string. The format of the string is the same as the one that
         * was generated by the {@link License#toString()}.
         * <p>
         * The syntax is more relaxed than in case of {@link License#toString()}, however. The spaces at the start
         * of the lines, before the : and the type name and the = sign are removed. In case of multi-line string
         * the spaces before and after the end string are removed.
         *
         * @param text the license in String format
         * @return the license
         */
        public static License from(final String text) {
            final var license = new License();
            try (var reader = new BufferedReader(new StringReader(text))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    final var parts = Feature.splitString(line);
                    final var name = parts[0];
                    final var typeString = parts[1];
                    final var valueString = getValueString(reader, parts[2]);
                    license.add(Feature.getFeature(name, typeString, valueString));
                }
                return license;
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }

        /**
         * Get the value string from the 'valueString' that is after the '=' character in the feature definition
         * string and from the buffered reader that optionally supply the following lines.
         * <p>
         * If the {@code valueString} does not start with the characters with {@code &lt;&lt;} the it is simply
         * returned. If it starts with the {@code &lt;&lt;} characters then the rest of the string is
         * interpreted as the {@code HERE_STRING} (see {@link #toString()}) and the subsequent lines are read from the
         * buffered reader {@code reader} till the {@code HERE_STRING} is found and the lines concatenated together
         * is returned as value string.
         *
         * @param reader      that supplies the consecutive lines from the text representation of the license that contains
         *                    the feature
         * @param valueString the value string that was on the first line of the feature definition.
         * @return the valueString compiled from the current line and presumably from subsequent lines.
         * @throws IOException if the reader throws, which should never happen as we read from a String
         */
        private static String getValueString(BufferedReader reader, String valueString) throws IOException {
            if (valueString.startsWith("<<")) {
                final var endLine = valueString.substring(2).trim();
                final var sb = new StringBuilder();
                String valueNextLine;
                while ((valueNextLine = reader.readLine()) != null) {
                    if (valueNextLine.trim().equals(endLine)) {
                        return sb.toString();
                    } else {
                        sb.append(valueNextLine);
                    }
                }
                throw new IllegalArgumentException("Multiline value string was not terminated before EOF.");
            }
            return valueString;
        }
    }
}
