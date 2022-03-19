# serializers.py
from rest_framework import serializers

from .models import Hero,  Book

class HeroSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Hero
        fields = ("id",'name', 'alias')

class BookSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Book
        fields = ("ISBN", "title", "author", "description", "edition","rating","books_available","book_Image_url" )
		