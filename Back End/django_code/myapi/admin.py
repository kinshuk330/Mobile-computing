from django.contrib import admin

# Register your models here.
from .models import Hero,Book
admin.site.register(Hero)
admin.site.register(Book)