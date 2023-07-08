student = [num for num in range(1,31)]
for m in range(1,29):
    i = int(input())
    student.remove(i)

min = min(student)
print(min)
student.remove(min)
student = student[0]
print(student)