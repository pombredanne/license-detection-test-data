require_relative 'spec_helper'
require_relative '../lib/Nouns/CopyrightHolders'

RSpec.describe CopyrightHolders do
    describe 'attributes' do
        it 'gets/sets copyright_policy_id with :copyright_policy_id' do
            subject.copyright_policy_id = '10'
            expect(subject.copyright_policy_id).to eq '10'
        end
        it 'gets/sets id with :id' do
            subject.id = '1'
            expect(subject.id).to eq '1'
        end
        it 'gets/sets name with :name' do
            subject.name = 'RSpecTest'
            expect(subject.name).to eq 'RSpecTest'
        end
    end
    it_behaves_like 'a json builder'
end