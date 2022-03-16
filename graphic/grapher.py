from matplotlib import pyplot as plt
from statistics import mean 

# Read input
file = open("tvsm1000p.txt", 'r')
InputLines = file.readlines()

times = []
m = []

for line in InputLines:
    str = line.strip().split(' ')
    times.append(int(str[0]))
    m.append(int(str[1]))

fig, ax = plt.subplots()
ax.scatter(m, times)
ax.set_title("Tiempo en funcion de la densidad con M=15")
ax.set_xlabel('M')
ax.set_ylabel('Tiempo (nanosegundos)')

plt.show()
plt.close(fig)
