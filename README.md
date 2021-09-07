# CovidCalculator
An app for calculating the trajectory of the covid pandemy. It uses a simple mathematical formula.
The app is based on a mathematical formula to calculate and predict:

1.The number of Total cases in a specified time interval

2.The number of Symptomatic cases in a specified time interval.

3.The number of Asymptomatic cases in a specified time interval.

4.The number of Deaths in a specified time interval.

5.A graph rapresenting all the numbers.


The app is fully customizable: The time interval, the starting number of cases, the growth factor, the % of asymptomatic cases, toogling the use of masks on/off. 
This customization feature was implemented so the app could work properly as our understanding of the virus evolves.

The formula:
Nd = No * (1 + E * p)^d

where

Nd=Number of cases after a specific period of time.

E=Average number of people someone infected is exposed to each day

p=Probability of each exposure becoming an infection

d=the total number of days from day 0

No = the starting number of cases.



