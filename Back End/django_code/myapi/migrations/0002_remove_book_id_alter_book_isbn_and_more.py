# Generated by Django 4.0.3 on 2022-03-19 15:17

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('myapi', '0001_initial'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='book',
            name='id',
        ),
        migrations.AlterField(
            model_name='book',
            name='ISBN',
            field=models.CharField(max_length=13, primary_key=True, serialize=False),
        ),
        migrations.AlterField(
            model_name='book',
            name='book_Image_url',
            field=models.URLField(null=True),
        ),
        migrations.AlterField(
            model_name='book',
            name='description',
            field=models.CharField(max_length=256),
        ),
        migrations.AlterField(
            model_name='book',
            name='edition',
            field=models.IntegerField(null=True),
        ),
        migrations.AlterField(
            model_name='book',
            name='rating',
            field=models.FloatField(null=True),
        ),
    ]
