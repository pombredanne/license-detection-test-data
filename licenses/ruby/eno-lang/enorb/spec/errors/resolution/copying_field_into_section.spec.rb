input = <<~DOC.strip
original: value

# copy < original
DOC

describe Eno::Errors::Resolution do
  describe '::copying_field_into_section' do
    let(:error) do
      intercept_parse_error { Eno.parse(input) }
    end

    it 'provides the correct message' do
      expect(error.message).to match_snapshot
    end

    it 'provides correct selection metadata' do
      expect(error.selection).to match_snapshot
    end
  end
end
