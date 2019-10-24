input = <<~DOC.strip
original:
entry = value

copy < original
- item
DOC

describe Eno::Errors::Resolution do
  describe '::copying_fieldset_into_list' do
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
