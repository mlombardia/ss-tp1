#!/usr/bin/python


import getopt
import matplotlib.pyplot as plt
import sys


def main(argv):
    StaticOutput = ''
    NeighbourOutput = ''
    try:
        opts, args = getopt.getopt(argv, "hi:o:", ["ifile=", "ofile="])
    except getopt.GetoptError:
        print('visual.py -i <StaticOutput> -o <NeighbourOutput>')
        sys.exit(2)
    for opt, arg in opts:
        if opt == '-h':
            print('visual.py -i <StaticOutput> -o <NeighbourOutput>')
            sys.exit()
        elif opt in ("-i", "--ifile"):
            StaticOutput = arg
        elif opt in ("-o", "--ofile"):
            NeighbourOutput = arg
    print('Static file is "{}"'.format(StaticOutput))
    print('Neighbour file is "{}"'.format(NeighbourOutput))

    time = 0
    quantity = 0
    l = 0
    particleIds = []
    particleRadius = []
    particleXCoords = []
    particleYCoords = []
    neighbours = []
    
    rc = 0

    file1 = open(StaticOutput, 'r')
    InputLines = file1.readlines()

    count = 0

    for line in InputLines:
        if count == 0:
            str = line.strip().split(' ')
            quantity = int(str[0])
        elif count == 1:
            str = line.strip().split(' ')
            l = float(str[0])

        elif count >= 2:
            str = line.strip().split(' ')
            particleIds.append(int(str[0]))
            ##print(int(str[0]))
            particleRadius.append(float(str[1]))
            particleXCoords.append(float(str[2]))
            particleYCoords.append(float(str[3]))

        count += 1



    file2 = open(NeighbourOutput, 'r')
    OutputLines = file2.readlines()

    count = 0

    for line in OutputLines:
        if count == 0:
            str = line.strip().split(' ')
            time = int(str[0])
        elif count == 1:
            str = line.strip().split(' ')
            rc = float(str[0])
        elif count > 2:
           # str = line.strip().replace(': [ ', ' ')
            #str = str.replace(' ]', '')
            str = line.strip().split(' ')
            aux = []
            j = 1
            while j < len(str):
                aux.append(int(str[j]))
                j += 1
            neighbours.append(aux)

        count += 1

    exit = 0

    while exit == 0:
        val = input("Enter particle id or exit: ")
        if val == 'exit':
            exit = 1
            break
        val = int(val)

        while val < 1 or val > quantity:
            val = input("Error, that particle id does not exist. Enter again: ")
            val = int(val)
        ##hasta aca todo bien
        colours = ['black'] * quantity

        colours[val] = 'r'

        ##aca flashea (creo que est porque estan defasados la lista en input y ouput)
        print(neighbours[val - 1])
        for n in neighbours[val - 1]:
            colours[n - 1] = 'b'

        lim = 0, l

        fig, ax = plt.subplots()

        i = 0
        while i < quantity:
            draw_circle = plt.Circle((particleXCoords[i], particleYCoords[i]), particleRadius[i], color=colours[i])
            ax.add_artist(draw_circle)
            i += 1
        draw_circle = plt.Circle((particleXCoords[val], particleYCoords[val]),
                                 rc + particleRadius[val], fill=False)
        ax.add_artist(draw_circle)
        draw_circle = plt.Circle((particleXCoords[val] - l, particleYCoords[val] - l),
                                 rc + particleRadius[val], fill=False)
        ax.add_artist(draw_circle)
        draw_circle = plt.Circle((particleXCoords[val] + l, particleYCoords[val] + l),
                                 rc + particleRadius[val], fill=False)
        ax.add_artist(draw_circle)
        draw_circle = plt.Circle((particleXCoords[val] + l, particleYCoords[val] - l),
                                 rc + particleRadius[val], fill=False)
        ax.add_artist(draw_circle)
        draw_circle = plt.Circle((particleXCoords[val] - l, particleYCoords[val] + l),
                                 rc + particleRadius[val], fill=False)
        ax.add_artist(draw_circle)
        draw_circle = plt.Circle((particleXCoords[val] + l, particleYCoords[val]),
                                 rc + particleRadius[val], fill=False)
        ax.add_artist(draw_circle)
        draw_circle = plt.Circle((particleXCoords[val], particleYCoords[val] + l),
                                 rc + particleRadius[val], fill=False)
        ax.add_artist(draw_circle)
        draw_circle = plt.Circle((particleXCoords[val] - l, particleYCoords[val]),
                                 rc + particleRadius[val], fill=False)
        ax.add_artist(draw_circle)
        draw_circle = plt.Circle((particleXCoords[val], particleYCoords[val] - l),
                                 rc + particleRadius[val], fill=False)
        ax.add_artist(draw_circle)

        ax.set_aspect('equal')
        ax.set_title("Neighbours of {}".format(val))
        ax.set_ylabel('Y')
        ax.set_xlabel('X')
        ax.set_ylim(lim)
        ax.set_xlim(lim)

        plt.show()
        plt.close(fig)

if __name__ == "__main__":
    main(sys.argv[1:])