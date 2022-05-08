# AI_Search_Scheduler

Applciation which takes an input file of courses, labs, timeslots, and constraints, and decides if there is a possible schedule that satisfies all of the constraints. The output will either be the schedule it finds with the lowest penalty, or "No schedule found".

To run, run the file Main.java with five arguments. The first argument is the path of the input file (sample test files are in the test_files directory). The next four arguments are the weights of the soft constraints. In order, they are: penalty for timeslots that have less than the minimum number of courses of labs, penalty for labs or courses not being put into a pre-assigned slot, two courses or labs that have a pair that is not satisfied, or two course sections at the same time.
