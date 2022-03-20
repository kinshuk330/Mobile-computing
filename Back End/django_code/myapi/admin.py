from django.contrib import admin

# Register your models here.
from .models import Hero,Book,Fine
admin.site.register(Hero)
admin.site.register(Book)
admin.site.register(Fine)