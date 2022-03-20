from django.shortcuts import render
from rest_framework import viewsets

from .serializers import HeroSerializer,BookSerializer, FineSerializer
from .models import Book, Hero, Fine
# Create your views here.
class HeroViewSet(viewsets.ModelViewSet):
    queryset = Hero.objects.all().order_by('name')
    serializer_class = HeroSerializer


class BookViewSet(viewsets.ModelViewSet):
    queryset = Book.objects.all().order_by('ISBN')
    serializer_class = BookSerializer

class FinesViewSet(viewsets.ModelViewSet):
    queryset = Fine.objects.all().order_by('due_date')
    serializer_class = FineSerializer