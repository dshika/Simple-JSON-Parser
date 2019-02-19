# Simple JSON Parser Utility

## '**.**' Notation

The objective of this utility is to easily convert a single line to nested JSON objects using the '.' notation.

For example, 

If the input provided to the parser is **Key1.Key2.Key3=Value**, the output will be 
  
  ```
  {
  Key1 : 
    Key2 : 
      Key3 : Value
  }
  ```
  
## Comma Separated Input String
  
The parser can take inputs as a comma separated string with '.' format to create the various levels of the output JSON object.
  
For example, 

If the input provided to the parser is as below,

```
Key1=Value1,Key2.Key21=Value2,Key3.Key31.Key311=Value3,Key3.Key32=Value4

```
Then the output generated will be as below,
```
{
Key1 : Value1
Key2 : 
  Key21 : Value2
Key3 : 
  Key31 :
    Key311 : Value3
  Key32 : Value4
}
