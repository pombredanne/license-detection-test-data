module Softlayer
  module Software
    class License < Softlayer::Entity
      attr_accessor :id
      attr_accessor :software_description_id
      attr_accessor :account
      attr_accessor :owner
      attr_accessor :software_description

      class Representer < Softlayer::Entity::Representer
        include Representable::Hash
        include Representable::Coercion
        property :id, type: Integer
        property :software_description_id, type: Integer
      end
    end
  end
end
