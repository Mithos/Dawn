CC=gcc
CFLAGS=`pkg-config --cflags --libs gstreamer-0.10` -I/usr/include/ -L/usr/lib

SRCDIR=src/
BUILDDIR=build/

SOURCES=$(SRCDIR)scanner.c
OBJECTS=$(BUILDDIR)scanner.o

all:
	$(CC) -c $(SOURCES) -o $(OBJECTS) $(CFLAGS)
