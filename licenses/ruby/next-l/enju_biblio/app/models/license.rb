class License < ApplicationRecord
  include MasterModel
  default_scope { order('licenses.position') }
end

# == Schema Information
#
# Table name: licenses
#
#  id                        :bigint           not null, primary key
#  name                      :string           not null
#  display_name              :string
#  note                      :text
#  position                  :integer
#  created_at                :datetime         not null
#  updated_at                :datetime         not null
#  display_name_translations :jsonb            not null
#
