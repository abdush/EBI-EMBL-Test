# EBI-EMBL-Test
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