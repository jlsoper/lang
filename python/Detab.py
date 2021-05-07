#!/usr/bin/python3

import sys

"""
 This class replaces tabs with spaces.
 @author James Soper
 @version 1.0
"""
class Detab:
  TAB   = chr(9)
  LF    = chr(10)
  CR    = chr(13)
  SPACE = chr(32)
  EOF   = ''  # End of File character, is empty char in Python

  inputFile  = None
  outputFile = None

  def __init__(self):
    self.tabsize = 2

  def setFileNames(self, argc):
    if argc == 2:
      self.inputFile = sys.argv[1]
    elif argc == 3:
      self.tabsize   = int(sys.argv[1])
      self.inputFile = sys.argv[2]
    else:
      print ("usage: python3 " + self.__class__.__name__ + ".py <tab_size> input_file")
      sys.exit(0)

  def processFile(self):
    tab = 0
    with open(self.inputFile, 'r') as reader:

      self.outputFile = self.inputFile + ".detab"

      with open(self.outputFile, 'w') as writer:
        i = reader.read(1)

        while i != self.EOF:
          if i == self.TAB:  # if tab found, output appropriate # of spaces
            j = tab

            while j < self.tabsize:
              writer.write(self.SPACE)
              j += 1

            tab = 0
          else:  # write non-tab character to output file
            writer.write(i)
            tab += 1

            if tab == self.tabsize:
              tab = 0

            if (i == self.CR) or (i == self.LF):
              tab = 0

          i = reader.read(1)

# Create the Detab object
obj = Detab()

obj.setFileNames(len(sys.argv))
obj.processFile()

