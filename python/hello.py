#!/usr/bin/python3

# sudo apt-get update
# sudo apt-get -y upgrade
# sudo apt-get install build-essential libssl-dev libffi-dev python-dev
# sudo apt-get install -y python3-venv
# python3 -m venv my_env
# source my_env/bin/activate
# deactivate

print("Hello, World! \n")

# Function to convert number into string
# Switcher is dictionary data type here
def numbers_to_strings(arg):
  switcher = {
    0: "zero",
    1: "one",
    2: "two",
  }

  # get() method of dictionary data type returns
  # value of passed argument if it is present
  # in dictionary otherwise second argument will
  # be assigned as default value of passed argument
  return switcher.get(arg, "nothing")

# Driver program
if __name__ == "__main__":
  argument = 1
  print (numbers_to_strings(argument))

