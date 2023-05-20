"""""""""""
Ori Dabush
212945760
01 - CS
ass07
"""""""""""


"""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
Function name: parser
Input: string line, list nameList
Output: string 
Function Operation: the function gets a line and checks if there are references in it, and add 
                    the names of the files they are referenced to the name list
"""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
def parser(line, nameList):
    # iterating on every index inthe line
    for i in range(len(line)):
        # check if there's a reference
        if line[i:].find('href="') != -1:
            # find the name of the file
            fileStart = line[i:].find('href="') + len('href="')
            fileEnd = line[i:].find('">')
            name = line[i:][fileStart:fileEnd]
            # check if the file haven't already added to the list
            if name not in nameList and name != '':
                # add the file name to the list
                nameList.append(name)


"""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
Function name: crawler
Input: string file, dictionary dictOfFiles
Output: none
Function Operation: this function appends to the dictionary every reference it finds as a key and a 
                    list of the files it referened to as its value.
"""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
def crawler(file, dictOfFiles):
    # check if this file was already checked
    if file in dictOfFiles:
        return
    # create a list where all the references in file will be appended to
    nameOfRefs = []
    # open the file
    with open(file, "r") as currentFile:
        # create a list of the lines of file
        linesList = currentFile.readlines()
        for line in linesList:
            # find the referenced file name in the current line
            parser(line, nameOfRefs)
    # add the file name into the dictionary as a key and the list of the references as its value
    dictOfFiles[file] = nameOfRefs
    # use the crawler in recursion and checking all the references in the current file
    for anotherFile in dictOfFiles[file]:
        crawler(anotherFile, dictOfFiles)


"""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
Function name: dictionaryToCSV
Input: dictionary dict
Output: none
Function Operation: this function create a new csv file named 'result.csv' and write in every line
                    a key and its value list's item separated with ','.
"""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
def dictionaryToCSV(dict):
    # open a new file named 'results.csv'
    with open('results.csv', 'w+') as file:
        # iterate on all the keys of the dictionary
        for objects in dict:
            # write the source file name
            file.write(objects)
            # iterate on the list of the referenced files in the current file and write them too, separated with ','
            for links in dict[objects]:
                file.write(',' + links)
            file.write('\n')


# get the source file name
firstFile = input('enter source file:\n')
# create a new dictionary that will contain the data of the files and references
dictOfLinks = {}
# use the crawler for the entered file name
crawler(firstFile, dictOfLinks)
# create the new CSV file by the dictionary that the crawler made
dictionaryToCSV(dictOfLinks)
# get the file name
filename = input('enter file name:\n')
# sort the list of the references in the entered file
dictOfLinks[filename].sort()
# print the list of the references in the entered file
print(dictOfLinks[filename])