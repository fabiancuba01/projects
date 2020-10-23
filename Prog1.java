
import java.util.Random;

class MagicSquare{
private int size, tryCt, row, col, magicSum, sizeSqr;
private int[] num;
private int[][] square;
private boolean found;
private Random randNum;
  
public MagicSquare(int size){
this.size = size;
this.row = size;
this.col = size;
this.square = new int[row][col];
this.found = false;
this.magicSum = ((size * (size * size + 1)) / 2);
this.sizeSqr = size * size;
this.num = new int[sizeSqr];
this.randNum = new Random();

for(int i = 0; i < row; i++){

for(int j = 0; j < col; j++){
          
this.square[i][j] = 0;
}
}

for(int i = 0; i < (sizeSqr); i++){
num[i] = (i + 1);
}
}

// purely random -- give up after "tries" tries
public int purelyRandom(int tries)
   {
int result = 0;

int[] sortedNum;
sortedNum = num.clone();
while (!found && tryCt < tries){
       
int tempNumSize = sizeSqr;
  
for(int i = 0; i < row; i++){
           
for(int j = 0; j < col; j++){
               
int tempRandNum = randNum.nextInt(tempNumSize);
square[i][j] = num[tempRandNum];
int tempNum = num[tempRandNum];
num[tempRandNum] = num[tempNumSize-1];
num[tempNumSize-1] = tempNum;
tempNumSize--;
}
}

found = magic();
num = sortedNum.clone();
tryCt++;
}
  

if (found)
result = tryCt;
else
result = -1;
  
return result;
}
  
// force last number in each row
public int endOfRow(int tries){
   
while (!found && tryCt < tries){
       
boolean ok = true;
int tempNumSize = sizeSqr, rowSum = 0, lastIndex = 0, pick = 0;
  
int[] sortedNum;
sortedNum = num.clone();

// fills rows one at a time
for (int rowCt = 0; rowCt < size && ok; rowCt++){
           
rowSum = 0;
// put random values in all but the last spot
for (int colCt = 0; colCt < size - 1; colCt++){
               
int tempRandNum = randNum.nextInt(tempNumSize);
square[rowCt][colCt] = num[tempRandNum];
rowSum += num[tempRandNum];
int tempNum = num[tempRandNum];
num[tempRandNum] = num[tempNumSize-1];
num[tempNumSize-1] = tempNum;
tempNumSize--;
}
  
lastIndex = tempNumSize;
pick = find(magicSum - rowSum, num, lastIndex);

if (pick == -1)
ok = false;
else{
int tempNum = num[pick];
num[pick] = num[tempNumSize-1];
num[tempNumSize-1] = tempNum;
tempNumSize--;
square[rowCt][size-1] = tempNum;
   }
}
found = magic();
num = sortedNum.clone();
tryCt++;
}
  
if (found){

}else
    {tryCt = -1;
}

return tryCt;
}

private int find(int magSum, int[] n , int lNum) {
int result = -1;
for(int i=0; i<lNum; i++) {
if(n[i] == magSum){
result = i;}
}
return result;}

public int pairs(int tries) {
        boolean found = false;
        int tryCt = 0;
        int result;
        int magicNum = (size * ((size * size) + 1)) / 2;
        while (!found && tryCt < tries) {
            int[] randArray = new int[size * size];
            for (int i = 0; i < randArray.length; i++) {
                randArray[i] = i + 1;
            }

            // Randomize randArray
            for (int i = 0; i < randArray.length; i++) {
                int r = i + new Random().nextInt(size * size - i);
                int temp = randArray[i];
                randArray[i] = randArray[r];
                randArray[r] = temp;
            }

            boolean ok = true;
            int i = size * size - 1;
            for (int row = 0; row < size && ok; row++) {
                for (int col = 0; col < size && ok; col += 2) {
                    square[row][col] = randArray[i];
                    int needFindPair = magicNum / (size / 2) - square[row][col];
                    int pick = find(needFindPair, randArray, i + 1);
                    i -= 1;
                    if (pick != -1) {
                        int temp = randArray[i];
                        randArray[i] = randArray[pick];
                        randArray[pick] = temp;
                        square[row][col + 1] = randArray[i];
                    } else {
                        ok = false;
                    }
                    i -= 1;
                }
            }
            if (magic()) {
                found = true;
            }
            tryCt++;
        }
        if (found) {
            result = tryCt;
        } else {
            result = -1;
        }
        return result;
    }



  
private boolean magic(){
   
int rowSum = 0, colSum = 0, diagnolSum1 = 0, diagnolSum2 = 0, trueDiag = 0, trueRow = 0, trueCol = 0;

//This checks all rows and columns to see if they add up to the magic sum if they do add one to trueRow or trueCol
for(int i = 0; i < row; i++){
       
for(int j = 0; j < col; j++){
           
rowSum += square[i][j];
colSum += square[j][i];
}
if(rowSum == magicSum){
           
trueRow++;
}
if(colSum == magicSum){
           
trueCol++;
}
rowSum = 0;
colSum = 0;
}

//checks the two diagnols to see if they are equal to magic sum
int tempSize = size-1;
  
for(int i = 0; i < size; i++){
       
diagnolSum1 += square[i][i];
diagnolSum2 += square[tempSize][i];
tempSize--;
}

if(diagnolSum1 == magicSum){
       
trueDiag++;
}
if(diagnolSum2 == magicSum){
       
trueDiag++;
}

//This function will only return true if all rows and colums are equal to the magic number as well as both diagnols
if (trueRow == size && trueCol == size && trueDiag == 2){
       
found = true;
}

return found;
}
  
// output the magic square (or whatever is in the array if it is not)
public void out(){
int row, col;
for (row = 0; row < size; row++)
       {
for (col = 0; col < size; col++)
           {
System.out.print(String.format("%3d", square[row][col]));
}
System.out.println();
}
}
  


public boolean rowLastImplemented(){
return true;
}
  

public boolean pairsImplemented()
   {
return true;
}
  

public static String myName()
   {
return "Fabian Cuba";
}
}