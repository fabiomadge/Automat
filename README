# Automat

* Automat* is a simple Java implementation of a formal language designed by me to model a finite-state machine. Since it was made with my basic CS class in mind, you can expect anything but feature completion.

The [wiki](https://www.github.com/fabiomadge/Automat/wiki) will help you, if you wish to start out fresh.

## The basics of the language

Let's start with a example:

	ab; 0: 'a'->1 'b'->0; 1: 'a'->1 'b'->0; 0,1

The languages most prominent separator is ';'.
The first part contains a string representing Σ, the input alphabet.
The last part contains F, the set of final sets. They are separated by ','.
The remaining fragments introduce the states and their state-transition function and are unlimited.

	<name>: '<char>'-><name>

Instead of typing out a state translation for every element of the alphabet, you can also use the default agent. It takes any input but the otherwise supplied translations.

	tired: 's'->awake default->tired
	
The name of a state can be anything that can be stored in a String.

## Quick start

The easiest way to get started is to simply clone the repository:

	git clone git://github.com/fabiomadge/Automat.git

## Building

.. should be as easy as..

	make

## Bug tracker

When finding a bug in the code I encourage you to file a bug. To do so please open a [ticket](https://github.com/fabiomadge/Automat/issues). You can submit feature requests as well.
