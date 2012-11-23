# Declarative UI Behavior with Fruit

_22 Oct 2011_

[![Build Status](http://jenkins.jamestastic.com/job/fruit/badge/icon)](http://jenkins.jamestastic.com/job/fruit/)

Fruit is a framework for building UI workflows declaratively using continuations and functional reactive programming.

To run the demo, first build the Scala compiler plugin:

```bash
> sbt/sbt "fruit-plugin/publish-local"
```

Then run the demo:

```bash
> sbt/sbt "fruit-demos/run-main fruit.FruitDemo"
```

Defining behavior in the conventional way via observers leads to some pretty ugly code. After reading [Deprecating the Observer Pattern](http://lamp.epfl.ch/~imaier/pub/DeprecatingObserversTR2010.pdf), a paper by Martin Odersky, Tiark Rompf, and Ingo Maier, I decided to put together a little framework for writing UI behavior in a declarative style, using delimited continuations and functional reactive programming take the mess of wiring up `ActionListener`s and sweep it under the rug. The outcome of this effort is Fruit, the Functional Reactive UI Thing.

Consider the following UI:

![Fruit screenshot 1](https://raw.github.com/JamesEarlDouglas/fruit/master/readme/fruit-1.png)

This panel includes a combo box and a label. Changes to the combo box selection are reflected in the label:


![Fruit screenshot 2](https://raw.github.com/JamesEarlDouglas/fruit/master/readme/fruit-2.png)

![Fruit screenshot 3](https://raw.github.com/JamesEarlDouglas/fruit/master/readme/fruit-3.png)

Conventionally this would require adding to the combo box an `ActionListener` which would encapsulate the (relatively simple) logic of what to do with updates to the combo box selection; in this case, updating the text of the label. With Fruit, it is as simple as a one-line declaration:

```scala
label1.setText(signal(combo1))
```

This treats the combo box as a signal, and the text of the label is set to the time-varying value of the combo box signal. This can be used to build up more complex UI workflows:

```scala
label1.setText(signal(combo1))
label2.setText(signal(combo2))
label3.setText(signal(combo1) + " is " + signal(combo2))
label4.setText(signal(combo1) + " ain't " + signal(combo2))
```

Here we have four labels which are updated based on the selection of the first combo box, the selection of the second combo box, or both. It keeps the UI workflow together, and is much easier to reason about than with observer implementations scattered about.

![Fruit screenshot 4](https://raw.github.com/JamesEarlDouglas/fruit/master/readme/fruit-4.png)

![Fruit screenshot 5](https://raw.github.com/JamesEarlDouglas/fruit/master/readme/fruit-5.png)

![Fruit screenshot 6](https://raw.github.com/JamesEarlDouglas/fruit/master/readme/fruit-6.png)

![Fruit screenshot 7](https://raw.github.com/JamesEarlDouglas/fruit/master/readme/fruit-7.png)

![Fruit screenshot 8](https://raw.github.com/JamesEarlDouglas/fruit/master/readme/fruit-8.png)

