# project-2

To Run:

```bash
git clone https://github.com/etai-shuchatowitz/project-2
mvn clean package
mvn exec:java -Dexec.mainClass="Main"
```

Or run from your IDE of choice

The program runs for all files in a given directory so to run this on a new file simply add that file to

`src/main/resources/unknown`

Upon running you will print out 

```
Confusion matrix
------------------------------------
3 0 0 
0 4 0 
0 0 3 
------------------------------------
```

And the corresponding precision, recall and fMeasure are
```
vals are: StatData{precision=1.0, recall=1.0, fMeasure=1.0}
vals are: StatData{precision=1.0, recall=1.0, fMeasure=1.0}
vals are: StatData{precision=1.0, recall=1.0, fMeasure=1.0}
```

The optimal K seems to be K=6. This gets the highest percentage of accuracy without overfitting the data.