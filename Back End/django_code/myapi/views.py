from django.shortcuts import render
from rest_framework import viewsets

from .serializers import HeroSerializer,BookSerializer
from .models import Book, Hero
# Create your views here.
class HeroViewSet(viewsets.ModelViewSet):
    queryset = Hero.objects.all().order_by('name')
    serializer_class = HeroSerializer


class BookViewSet(viewsets.ModelViewSet):
    queryset = Book.objects.all().order_by('ISBN')
    serializer_class = BookSerializer