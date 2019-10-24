require 'spec_helper'

describe Wikimedia::Commoner do
  describe '#licence' do

    context 'of a Delhi portrait of a man' do
      let(:image) {
        VCR.use_cassette("details/#{self.class.description}".gsub(" ","-")) {
          Wikimedia::Commoner.details('https://commons.wikimedia.org/wiki/File:India_-_Delhi_portrait_of_a_man_-_4780.jpg')
        }
      }
      it 'should be CC BY-SA 3.0' do
        expect(image[:licence]).to eq('CC BY-SA 3.0')
      end
    end

    context 'of Nahal Zaror, south 11' do
      let(:image) {
        VCR.use_cassette("details/#{self.class.description}".gsub(" ","-")) {
          Wikimedia::Commoner.details('File:Nahal_Zaror,_south_11.jpg')
        }
      }
      it 'should be CC BY-SA 3.0' do
        expect(image[:licence]).to eq('CC BY-SA 3.0')
      end
      it 'should have a licence url' do
        expect(image[:licence_url]).to eq('https://creativecommons.org/licenses/by-sa/3.0')
      end
    end

    context 'of The mohave desert near the fossil beds' do
      let(:image) {
        VCR.use_cassette("details/#{self.class.description}".gsub(" ","-")) {
          Wikimedia::Commoner.details('File:PSM_V86_D252_The_mohave_desert_near_the_fossil_beds.jpg')
        }
      }
      it 'should be Public Domain' do
        expect(image[:licence]).to eq('Public domain')
      end
    end

    context 'of a Spanish Civil War mass grave' do
      let(:image) {
        VCR.use_cassette("details/#{self.class.description}".gsub(" ","-")) {
          Wikimedia::Commoner.details('File:Spanish_Civil_War_-_Mass_grave_-_Estépar,_Burgos.jpg')
        }
      }
      it 'should be CC BY-SA 4.0' do
        expect(image[:licence]).to eq('CC BY-SA 4.0')
      end
    end

    context 'of August Wilhelm von Hofmann' do
      let(:image) {
        VCR.use_cassette("details/#{self.class.description}".gsub(" ","-")) {
          Wikimedia::Commoner.details('File:Hoffman_August_Wilhelm_von.jpg')
        }
      }
      it 'should be CC-PD-Mark' do
        expect(image[:licence]).to eq('Public domain')
      end
      it 'should link to the public domain licence page' do
        expect(image[:licence_url]).to eq('https://creativecommons.org/publicdomain/mark/1.0')
      end
    end

  end
end
