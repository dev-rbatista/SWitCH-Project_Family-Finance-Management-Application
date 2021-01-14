# Use Case Diagram

```puml
left to right direction

actor "Family Member" as fm
actor "Family Administrator" as fa
actor "System Manager" as sm
usecase "UC001 - to create a standard category" as uc001
usecase "UC002 - to get the standard categories \ntree" as uc002
usecase "UC010 - to create a family" as uc010
usecase "UC011 - to add a family administrator" as uc011
usecase "UC101 - to add family members" as uc101
usecase "UC104 - to get the list of family members \nand their relations" as uc104
usecase "UC105 - to create a relation between two \nfamily members" as uc105
usecase "UC110 - to get the list of the categories \non the family’s category tree" as uc110
usecase "UC120 - to create a family cash account" as uc120
usecase "UC150 - to get my profile’s information" as uc150
usecase "UC151 - to add an email account to my \nprofile" as uc151

sm -- uc001
sm -- uc002
sm -- uc010
sm -- uc011
fa -- uc101
fa -- uc104
fa -- uc105
fa -- uc110
fa -- uc120
fm -- uc150
fm -- uc151
```