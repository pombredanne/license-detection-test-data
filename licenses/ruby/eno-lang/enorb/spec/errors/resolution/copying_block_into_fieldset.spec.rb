input = <<~DOC.strip
-- original
value
-- original

copy < original
entry = value
DOC

describe Eno::Errors::Resolution do
  describe '::copying_block_into_fieldset' do
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
