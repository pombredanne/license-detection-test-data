/*
 * Copyright (C) 2017 Timo Vesalainen <timo.vesalainen@iki.fi>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.vesalainen.vfs.pm.deb;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import static org.vesalainen.vfs.pm.deb.Field.*;

/**
 *
 * @author Timo Vesalainen <timo.vesalainen@iki.fi>
 */
public class Copyright extends ControlBase
{
    private Paragraph paragraph1 = new Paragraph();
    private Paragraph paragraph2 = new Paragraph();
    private Map<Path,Paragraph> files = new HashMap<>();
    
    public Copyright()
    {
        super("copyright", new Paragraph(), new Paragraph());
        paragraph1 = this.paragraphs.get(0);
        paragraph2 = this.paragraphs.get(1);
        paragraph1.add(FORMAT_SPECIFICATION, "http://svn.debian.org/wsvn/dep/web/deps/dep5.mdwn?op=file&rev=135");
    }

    public Copyright(Path debian) throws IOException
    {
        super(debian, "copyright");
        switch (paragraphs.size())
        {
            default:
            case 2:
                paragraph2 = this.paragraphs.get(1);
            case 1:
                paragraph1 = this.paragraphs.get(0);
                break;
            case 0:
                break;
        }
    }

    @Override
    protected boolean checkFirstLine(String line)
    {
        return line.startsWith("Format");
    }
    public Copyright setName(String v)
    {
        paragraph1.add(NAME, v);
        return this;
    }
    public String getName()
    {
        return paragraph1.get(NAME);
    }
    public Copyright setMaintainer(String v)
    {
        paragraph1.add(MAINTAINER, v);
        return this;
    }
    public String getMaintainer()
    {
        return paragraph1.get(MAINTAINER);
    }
    public Copyright setSource(String v)
    {
        paragraph1.add(SOURCE, v);
        return this;
    }
    public String getSource()
    {
        return paragraph1.get(SOURCE);
    }
    public Copyright setCopyright(String v)
    {
        paragraph2.add(COPYRIGHT, v);
        return this;
    }
    public String getCopyright()
    {
        return paragraph2.get(COPYRIGHT);
    }
    public Copyright setLicense(String v)
    {
        paragraph2.add(LICENSE, v);
        return this;
    }
    public String getLicense()
    {
        return paragraph2.get(LICENSE);
    }
    public FileCopyright addFile(Path file)
    {
        Paragraph p = new Paragraph();
        paragraphs.add(p);
        files.put(file, p);
        return new FileCopyright(p, file);
    }
    public class FileCopyright
    {
        private Paragraph p;

        public FileCopyright(Paragraph p, Path file)
        {
            this.p = p;
            p.add(FILES, file.toString());
        }
        public String getFiles()
        {
            return p.get(FILES);
        }
        public FileCopyright addCopyright(String copyright)
        {
            p.add(COPYRIGHT, copyright);
            return this;
        }
        public String getCopyright()
        {
            return p.get(COPYRIGHT);
        }
        public FileCopyright addLicense(String license)
        {
            p.add(LICENSE, license);
            return this;
        }
        public String getLicense()
        {
            return p.get(LICENSE);
        }
    }
}
