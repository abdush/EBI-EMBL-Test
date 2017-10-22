# EBI-EMBL
**EBI_010230	Code	Test**

Please	complete	the	following	three	problems.	When	submitting	your	code,	
please	state	which	Java	version	you	have	used.	The	code	for	each	problem	should	
be	submitted	within	it’s	own	project/directory	and	the	main	class	should	be	the	
problem	number,	e.g.	ProblemOne.

`Problem	One`

You	have	been	supplied	the	TSV	file	“input_data.txt”.	Write	code	that	will	process	
this	input	file	and	transform	the	data	into	a	TSV	file	“output_data.txt”.	The	output	
file	should	contain	one	row	per	sample	and	hold	the	sample	ID,	cell	type,	cell	line,	
sex,	depth,	collection	date	and	latitude	and	longitude.	The	first	line	of	the	output	
file	should	contain	the	column	headers.
Note	that	different	attribute	tags	may	be	used	to	represent	a	piece	of	
information.	The	“attribute_mappings.xlsx”	file	contains	the	mappings	for	each	of	
these.
Your	code	should	be	able	to	compile	and	run	via	a	command	line	call.

`Problem	Two`

Please	alter	your	solution	for	Problem	One	to	include	considering	when	a	range	
has	been	supplied	for	collection	date	and	depth.
Your	code	should	be	able	to	compile	and	run	via	a	command	line call.
	
`Problem	Three`

In	the	previous	problems	you	were	reading	from	and	writing	to	a	TSV	file.	
Consider	if	you	were	now	reading	from	and	writing	to	a	database	instead.	Please	
alter	your solution	for	Problem	Two	to	replace	the	TSV	files	with	database	tables.
You	will	need	to	include	a	step	to	create	the	table	for	your	output	data.
Your	solution	should	use	JDBC.	Do	not	assume	that	the	input	and	output	tables	
are	within	the	same	database.
Your	code	should	be	able	to	compile	and	we	may	set	the	connection	details	to	
internal	test	databases	to	test	how	they	run.
_________________________________________________________________________


`How to run`

_**build:**_ From the project directory (ex. ProblemOne), run command _**mvn clean package**_

_**execute:**_ From the project directory (ex. ProblemOne), run command _**java -jar target\<jar-file-name>**_

ex. java -jar target\problem-one.jar

Or

Use the provided bash script:

_**build:**_ run command _**./service.sh build**_

_**execute:**_ run command _**./service.sh run**_


**Note:** All the dependencies are located in a _dependency-jar_ directory with the jar file location.


`application properties`

The program uses an application properties file to set the input/output file paths or DB resources.
The file is located in the _src/main/resources_ directory. Update it with the desired values 
before building and running the program.

**_Used properties:_**
 - _mapping.file_ : for the attributes mapping file path.
 - _input.file_ : for the samples input TSV file.
 - _output.file_ : for the samples output TSV file.
 - _input.db.url_: url for the db where input sample data is stored.
 - _input.db.user_: user for the input db.
 - _input.db.pass_: password for the input db.
 - _input.db.table_: table name in input db where sample data are read from.
 - _output.db.url_: url for the db where output sample data is stored.
 - _output.db.user_: user for the output db.
 - _output.db.pass_: password for the output db.
 - _output.db.table_: table name in output db where sample data are written to.
 
 `Results`
 
 - For each of the sample attributes, whenever there is more than one value for given sample 
 the values are concatenated together (using ' | ' symbol). 
 - For latitude & longitude values: because sometimes both values are provided at once while 
 sometimes individually in a separate line, some further action need to be taken to have a 
 uniform representation as (lat | long). The attributes for latitude & longitude are matched into three groups and used accordingly; lat only, lon only, and both lat and lon.
 - Depth values are investigated and possible range values were assumed to have format like '0-15'. 
 This is translated into depth start of 0 and depth end of 15. Though there is a depth_start 
 and depth_end attributes in the mapping file, it was not found in the input file.
 - Collection date values are investigated and possible range values were assumed to have format like 
 '1980/2015' where '/' separates two dates or years. This is translated into start of 1980 and end of 2015. 
 - Most of the attributes data values does not represent a single data type, therefore all 
 are handled as strings.     
 
 `Jupyter python notebook`
  
  This samll python notebook is created to help in getting some sense of the sample data.
   [EBI Sample Jupyter notebook](jupyter_notebook.md)