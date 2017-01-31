#JSearchFX - Find, highlight and select JavaFX Nodes
There is a problem with big applications: The user has to remember where to find things.
Things like Tabs and TitledPanes can make it really hard to find things.

This problem is what JSearchFX tries to solve. It allows you to create a Searchengine or a Searchfield which finds nearly everything in your application.
Here is how to do it:

##Create a Searchengine for a JavaFX layout
```
//Create the Layout
BorderPane t = FXMLLoader.load(...);

//Create a Searchengine for the Layout and its children
JSearchEngine<NodeSearch> engine = new JSearchFX().createSearchEngine(t);

//Search
ArrayList<JSearchResult<NodeSearch>> result = engine.query("circle");
```

##Create a Searchfield for a JavaFX layout
```
//Create the Layout
BorderPane t = FXMLLoader.load(...);

//Create an Effect to highlight Nodes
Effect e = new SepiaTone();

//Create the Searchfield
//Third parameter: true if you want to select the best result on enter
TextField search = AppSearchField.make(t, e, true);
```

##Custom search conditions
To customize when to search and what to do with the results, please take a look at the SearchChangeListener class.
