from django.db import models
from django.core.validators import MinLengthValidator, int_list_validator
from django.db.models import CheckConstraint, Q
from django.forms import CharField
from django.db.models.functions import Length
from accounts.models import User
def subject_validate(value):
    subjects=["maths","science","cs","biology","desgin"]
    if value.tolower() in subjects:
        return True
    return False

models.CharField.register_lookup(Length, 'length')

# Create your models here.
class Hero(models.Model):
    name = models.CharField(max_length=60)
    alias = models.CharField(max_length=60)
    def __str__(self):
        return self.name
class Book(models.Model):
    ### book details

    title=models.CharField(max_length=60)
    author=models.CharField(max_length=60)
    edition=models.IntegerField(null=True)
    description=models.CharField(max_length=256)
    ISBN=models.CharField(max_length=13, primary_key=True)
    subjects=models.CharField(max_length=60,validators=[subject_validate])

    rating=models.FloatField(null=True)
    books_available=models.CharField(max_length=60)
    book_Image_url=models.URLField(null=True)

    #for adding constraints on sql level
    class Meta:

        constraints = (
            # for checking in the DB
            models.CheckConstraint(
                check=Q(rating__gte=0.0) & Q(rating__lte=5.0),
                name='rating_range')
            ,
            models.CheckConstraint(
                check=Q(ISBN__length__gte=13) & Q(ISBN__length__lte=13),
                name='ISBN_Length_range')
            
            )

class Fine(models.Model):

    user_id = models.ForeignKey(User, on_delete=models.CASCADE,related_name = "Fine_Due_by")
    amount = models.FloatField(max_length=256)
    due_date = models.DateTimeField(null=True)
    
    

    
