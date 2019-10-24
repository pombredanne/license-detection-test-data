require_relative 'spec_helper'
require_relative '../lib/Nouns/CopyrightPolicies'

RSpec.describe CopyrightPolicies do
    describe 'attributes' do
        it 'gets/sets code with :code' do
            subject.code= 'RSpecTest'
            expect(subject.code).to eq 'RSpecTest'
        end
        it 'gets/sets description with :description' do
            subject.description = 'RSpecTest'
            expect(subject.description).to eq 'RSpecTest'
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