import cx_Oracle

db = cx_Oracle.connect("foresight/fore123@oraasgtd12-scan.nam.nsroot.net:8888/SFRMPOC")

cursor = db.cursor()
cursor.execute("select * from revs")

rows = cursor.fetchall()
print(rows)

