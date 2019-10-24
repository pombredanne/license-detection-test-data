from schematics.models import Model
from schematics.types import StringType


class License(Model):
    name = StringType(required=True)
    url = StringType()
