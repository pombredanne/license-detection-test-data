module Softlayer
  module Container
    module Product
      class Order
        module Software
          class License < Softlayer::Container::Product::Order

            class Representer < Softlayer::Container::Product::Order::Representer
              include Representable::Hash
              include Representable::Coercion
            end
          end
        end
      end
    end
  end
end
