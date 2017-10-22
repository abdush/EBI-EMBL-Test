
## EBI Sample Code

### Python imports


```python
from IPython.core.display import HTML
HTML("""
<style>
.output_png {
    display: table-cell;
    text-align: center;
    vertical-align: middle;
}
</style>
""")
```





<style>
.output_png {
    display: table-cell;
    text-align: center;
    vertical-align: middle;
}
</style>





```python
# remove warnings
import warnings
warnings.filterwarnings('ignore')
# ---

%matplotlib inline
import pandas as pd
from matplotlib import pyplot as plt
import matplotlib
import numpy as np

pd.options.display.max_columns = 100
pd.options.display.max_rows = 100

matplotlib.style.use('ggplot')
```

### Read the mapping (xlsx) file


```python
# Read the excel sheet to pandas dataframe
attr_mappings = pd.read_excel("dataset/attribute_mappings.xlsx", sheetname=0)
```


```python
attr_mappings.shape
```




    (28, 6)




```python
attr_mappings.head(28)
```




<div>
<style>
    .dataframe thead tr:only-child th {
        text-align: right;
    }

    .dataframe thead th {
        text-align: left;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>Cell type</th>
      <th>Sex</th>
      <th>Depth</th>
      <th>Cell line</th>
      <th>Collection date</th>
      <th>latitude and longitude</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>ArrayExpress-CELL_TYPE</td>
      <td>ArrayExpress-SEX</td>
      <td>Avg_Depth</td>
      <td>cel_line</td>
      <td>Colection Date</td>
      <td>geographic location (latitude and             ...</td>
    </tr>
    <tr>
      <th>1</th>
      <td>ArrayExpress-CellType</td>
      <td>ArrayExpress-Sex</td>
      <td>DEPTH</td>
      <td>cell  line</td>
      <td>colection date</td>
      <td>geographic location (latitude and longitude)</td>
    </tr>
    <tr>
      <th>2</th>
      <td>cell type</td>
      <td>SEX</td>
      <td>Depth</td>
      <td>cell  type</td>
      <td>collected date</td>
      <td>Geographic location (latitude and longitude)</td>
    </tr>
    <tr>
      <th>3</th>
      <td>Cell type</td>
      <td>sex</td>
      <td>depth</td>
      <td>cell _line_name</td>
      <td>Collection Date</td>
      <td>Geographic location (latitude, longitude)</td>
    </tr>
    <tr>
      <th>4</th>
      <td>Cell Type</td>
      <td>Sex</td>
      <td>Depth (m)</td>
      <td>cell ine</td>
      <td>collection date</td>
      <td>Geographic location (latitude)</td>
    </tr>
    <tr>
      <th>5</th>
      <td>NaN</td>
      <td>gender</td>
      <td>depth (m)</td>
      <td>cell l ine</td>
      <td>Collection date</td>
      <td>Geographic location (Latitude)</td>
    </tr>
    <tr>
      <th>6</th>
      <td>NaN</td>
      <td>NaN</td>
      <td>Depth (Meters)</td>
      <td>cell lien</td>
      <td>collection date (yyyymmdd)</td>
      <td>geographic location (latitude)</td>
    </tr>
    <tr>
      <th>7</th>
      <td>NaN</td>
      <td>NaN</td>
      <td>Depth_end</td>
      <td>cell line</td>
      <td>Collection_date</td>
      <td>Geographic location (longitude)</td>
    </tr>
    <tr>
      <th>8</th>
      <td>NaN</td>
      <td>NaN</td>
      <td>depth_end</td>
      <td>Cell line</td>
      <td>collection_date</td>
      <td>geographic location (longitude)</td>
    </tr>
    <tr>
      <th>9</th>
      <td>NaN</td>
      <td>NaN</td>
      <td>Depth_m</td>
      <td>Cell Line</td>
      <td>Collection_Date</td>
      <td>Geographic location (Longitude)</td>
    </tr>
    <tr>
      <th>10</th>
      <td>NaN</td>
      <td>NaN</td>
      <td>depth_start</td>
      <td>cell lline</td>
      <td>COLLECTION_DATE</td>
      <td>Geographical location (lat_lon)</td>
    </tr>
    <tr>
      <th>11</th>
      <td>NaN</td>
      <td>NaN</td>
      <td>Depth_start</td>
      <td>cell_line</td>
      <td>collection-date</td>
      <td>geographical location (longitude and longitude)</td>
    </tr>
    <tr>
      <th>12</th>
      <td>NaN</td>
      <td>NaN</td>
      <td>depth(cm)</td>
      <td>Cell_line</td>
      <td>collectiondate</td>
      <td>lat lon</td>
    </tr>
    <tr>
      <th>13</th>
      <td>NaN</td>
      <td>NaN</td>
      <td>depth(m)</td>
      <td>NaN</td>
      <td>Date Collected</td>
      <td>Lat lon</td>
    </tr>
    <tr>
      <th>14</th>
      <td>NaN</td>
      <td>NaN</td>
      <td>Geographic location (depth)</td>
      <td>NaN</td>
      <td>Date of Collection</td>
      <td>lat_lan</td>
    </tr>
    <tr>
      <th>15</th>
      <td>NaN</td>
      <td>NaN</td>
      <td>geographic location (depth)</td>
      <td>NaN</td>
      <td>NaN</td>
      <td>lat_lon</td>
    </tr>
    <tr>
      <th>16</th>
      <td>NaN</td>
      <td>NaN</td>
      <td>geographical location (depth)</td>
      <td>NaN</td>
      <td>NaN</td>
      <td>LAT_LON</td>
    </tr>
    <tr>
      <th>17</th>
      <td>NaN</td>
      <td>NaN</td>
      <td>Geographical location (Depth)</td>
      <td>NaN</td>
      <td>NaN</td>
      <td>Lat_lon</td>
    </tr>
    <tr>
      <th>18</th>
      <td>NaN</td>
      <td>NaN</td>
      <td>NaN</td>
      <td>NaN</td>
      <td>NaN</td>
      <td>Lat_Lon</td>
    </tr>
    <tr>
      <th>19</th>
      <td>NaN</td>
      <td>NaN</td>
      <td>NaN</td>
      <td>NaN</td>
      <td>NaN</td>
      <td>latitude</td>
    </tr>
    <tr>
      <th>20</th>
      <td>NaN</td>
      <td>NaN</td>
      <td>NaN</td>
      <td>NaN</td>
      <td>NaN</td>
      <td>Latitude</td>
    </tr>
    <tr>
      <th>21</th>
      <td>NaN</td>
      <td>NaN</td>
      <td>NaN</td>
      <td>NaN</td>
      <td>NaN</td>
      <td>latitude and longitude</td>
    </tr>
    <tr>
      <th>22</th>
      <td>NaN</td>
      <td>NaN</td>
      <td>NaN</td>
      <td>NaN</td>
      <td>NaN</td>
      <td>Latitude and longitude</td>
    </tr>
    <tr>
      <th>23</th>
      <td>NaN</td>
      <td>NaN</td>
      <td>NaN</td>
      <td>NaN</td>
      <td>NaN</td>
      <td>Latitude and Longitude</td>
    </tr>
    <tr>
      <th>24</th>
      <td>NaN</td>
      <td>NaN</td>
      <td>NaN</td>
      <td>NaN</td>
      <td>NaN</td>
      <td>latitude and longtitude</td>
    </tr>
    <tr>
      <th>25</th>
      <td>NaN</td>
      <td>NaN</td>
      <td>NaN</td>
      <td>NaN</td>
      <td>NaN</td>
      <td>latitude_longitude</td>
    </tr>
    <tr>
      <th>26</th>
      <td>NaN</td>
      <td>NaN</td>
      <td>NaN</td>
      <td>NaN</td>
      <td>NaN</td>
      <td>Longitude</td>
    </tr>
    <tr>
      <th>27</th>
      <td>NaN</td>
      <td>NaN</td>
      <td>NaN</td>
      <td>NaN</td>
      <td>NaN</td>
      <td>longitude</td>
    </tr>
  </tbody>
</table>
</div>



### List of the attribute mappings


```python
mapping_cell_type = attr_mappings[attr_mappings["Cell type"].notnull()]["Cell type"].tolist()
mapping_sex = attr_mappings[attr_mappings["Sex"].notnull()]["Sex"].tolist()
mapping_depth = attr_mappings[attr_mappings["Depth"].notnull()]["Depth"].tolist()
mapping_cell_line = attr_mappings[attr_mappings["Cell line"].notnull()]["Cell line"].tolist()
mapping_collection_date = attr_mappings[attr_mappings["Collection date"].notnull()]["Collection date"].tolist()
mapping_lat_long = attr_mappings[attr_mappings["latitude and longitude"].notnull()]["latitude and longitude"].tolist()
mapping_cell_type
```




    ['ArrayExpress-CELL_TYPE',
     'ArrayExpress-CellType',
     'cell type',
     'Cell type',
     'Cell Type']



### Read the sample TSV file


```python
#Read the samples TSV file
column_names = ['sampleId','attribute','value']
samples = pd.read_csv('dataset/input_data.txt', sep='\t', header=None, names=column_names, 
                      skip_blank_lines=False, index_col=False)
```


```python
samples.shape
```




    (10003, 3)




```python
samples.head()
```




<div>
<style>
    .dataframe thead tr:only-child th {
        text-align: right;
    }

    .dataframe thead th {
        text-align: left;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>sampleId</th>
      <th>attribute</th>
      <th>value</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>ERS008227</td>
      <td>geographic location (country)</td>
      <td>Burkina Faso:Boulkiemde province,Boulpon</td>
    </tr>
    <tr>
      <th>1</th>
      <td>ERS008227</td>
      <td>geographic location (latitude and longitude)</td>
      <td>2.066667 W 12.65 N</td>
    </tr>
    <tr>
      <th>2</th>
      <td>ERS008227</td>
      <td>sex</td>
      <td>female</td>
    </tr>
    <tr>
      <th>3</th>
      <td>ERS008228</td>
      <td>geographic location (country)</td>
      <td>Italy:Toscany, Florence</td>
    </tr>
    <tr>
      <th>4</th>
      <td>ERS008228</td>
      <td>geographic location (latitude and longitude)</td>
      <td>11.25 E 43.783333 N</td>
    </tr>
  </tbody>
</table>
</div>



### Look into the attributes data


```python
#How many unique samples
samples.sampleId.nunique()

#How many time each sample appears in a row
#samples.sampleId.value_counts()
```




    4607




```python
#How many unique attribute names
samples.attribute.nunique()
```




    44




```python
total_attributes = samples.sort_values(['attribute'], ascending=False).groupby("attribute")
total_attributes.count()
```




<div>
<style>
    .dataframe thead tr:only-child th {
        text-align: right;
    }

    .dataframe thead th {
        text-align: left;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>sampleId</th>
      <th>value</th>
    </tr>
    <tr>
      <th>attribute</th>
      <th></th>
      <th></th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>ArrayExpress-CellType</th>
      <td>6</td>
      <td>4</td>
    </tr>
    <tr>
      <th>ArrayExpress-DevelopmentalStage</th>
      <td>10</td>
      <td>8</td>
    </tr>
    <tr>
      <th>ArrayExpress-Sex</th>
      <td>214</td>
      <td>214</td>
    </tr>
    <tr>
      <th>Collection date</th>
      <td>36</td>
      <td>36</td>
    </tr>
    <tr>
      <th>Country</th>
      <td>3</td>
      <td>3</td>
    </tr>
    <tr>
      <th>Depth</th>
      <td>25</td>
      <td>25</td>
    </tr>
    <tr>
      <th>ENA-CHECKLIST</th>
      <td>2531</td>
      <td>2531</td>
    </tr>
    <tr>
      <th>Genotype</th>
      <td>77</td>
      <td>77</td>
    </tr>
    <tr>
      <th>Geographic location (Country)</th>
      <td>2</td>
      <td>2</td>
    </tr>
    <tr>
      <th>Geographic location (country:region,area)</th>
      <td>20</td>
      <td>20</td>
    </tr>
    <tr>
      <th>Geographic location (depth)</th>
      <td>2</td>
      <td>2</td>
    </tr>
    <tr>
      <th>Geographic location (latitude and longitude)</th>
      <td>21</td>
      <td>21</td>
    </tr>
    <tr>
      <th>Geographical location (lat_lon)</th>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>Investigation type</th>
      <td>31</td>
      <td>31</td>
    </tr>
    <tr>
      <th>Latitude</th>
      <td>47</td>
      <td>47</td>
    </tr>
    <tr>
      <th>Longitude</th>
      <td>47</td>
      <td>47</td>
    </tr>
    <tr>
      <th>Project name</th>
      <td>31</td>
      <td>31</td>
    </tr>
    <tr>
      <th>Sex</th>
      <td>226</td>
      <td>226</td>
    </tr>
    <tr>
      <th>Temperature</th>
      <td>12</td>
      <td>12</td>
    </tr>
    <tr>
      <th>Tissue Type</th>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>Tissue type</th>
      <td>6</td>
      <td>6</td>
    </tr>
    <tr>
      <th>body site</th>
      <td>5</td>
      <td>5</td>
    </tr>
    <tr>
      <th>cell type</th>
      <td>2</td>
      <td>2</td>
    </tr>
    <tr>
      <th>collected_by</th>
      <td>4</td>
      <td>4</td>
    </tr>
    <tr>
      <th>collection date</th>
      <td>104</td>
      <td>103</td>
    </tr>
    <tr>
      <th>collection_date</th>
      <td>2905</td>
      <td>2889</td>
    </tr>
    <tr>
      <th>country</th>
      <td>413</td>
      <td>413</td>
    </tr>
    <tr>
      <th>gender</th>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>genotype</th>
      <td>2</td>
      <td>2</td>
    </tr>
    <tr>
      <th>geographic location (Country)</th>
      <td>9</td>
      <td>9</td>
    </tr>
    <tr>
      <th>geographic location (country and/or sea)</th>
      <td>2744</td>
      <td>2744</td>
    </tr>
    <tr>
      <th>geographic location (country and/or sea,region)</th>
      <td>15</td>
      <td>15</td>
    </tr>
    <tr>
      <th>geographic location (country)</th>
      <td>139</td>
      <td>139</td>
    </tr>
    <tr>
      <th>geographic location (depth)</th>
      <td>61</td>
      <td>59</td>
    </tr>
    <tr>
      <th>geographic location (latitude and longitude)</th>
      <td>111</td>
      <td>111</td>
    </tr>
    <tr>
      <th>geographic location (latitude)</th>
      <td>4</td>
      <td>3</td>
    </tr>
    <tr>
      <th>geographic location (longitude)</th>
      <td>4</td>
      <td>3</td>
    </tr>
    <tr>
      <th>geographical location (depth)</th>
      <td>9</td>
      <td>9</td>
    </tr>
    <tr>
      <th>geographical location (longitude and longitude)</th>
      <td>9</td>
      <td>9</td>
    </tr>
    <tr>
      <th>investigation type</th>
      <td>59</td>
      <td>58</td>
    </tr>
    <tr>
      <th>lat_lon</th>
      <td>1</td>
      <td>0</td>
    </tr>
    <tr>
      <th>sex</th>
      <td>35</td>
      <td>35</td>
    </tr>
    <tr>
      <th>temperature</th>
      <td>15</td>
      <td>15</td>
    </tr>
    <tr>
      <th>tissue type</th>
      <td>1</td>
      <td>1</td>
    </tr>
  </tbody>
</table>
</div>




```python
samples.isnull().sum()
```




    sampleId      2
    attribute     2
    value        29
    dtype: int64




```python
# Check if there is a duplicate attribute assignment for a sample
# This checks by name, next need to unify all attribute mappings
sample_attr_grp = samples.groupby(['sampleId', 'attribute'])
sample_attr_grp.size().reset_index(name='Freq')
sample_attr_duplicate = sample_attr_grp.filter(lambda x: len(x) > 1)
sample_attr_duplicate
```




<div>
<style>
    .dataframe thead tr:only-child th {
        text-align: right;
    }

    .dataframe thead th {
        text-align: left;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>sampleId</th>
      <th>attribute</th>
      <th>value</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>8067</th>
      <td>ERS017732</td>
      <td>geographic location (country and/or sea,region)</td>
      <td>USA</td>
    </tr>
    <tr>
      <th>8068</th>
      <td>ERS017732</td>
      <td>geographic location (country and/or sea,region)</td>
      <td>USA</td>
    </tr>
  </tbody>
</table>
</div>




```python
samples[samples["sampleId"].isin(sample_attr_duplicate["sampleId"])]
```




<div>
<style>
    .dataframe thead tr:only-child th {
        text-align: right;
    }

    .dataframe thead th {
        text-align: left;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>sampleId</th>
      <th>attribute</th>
      <th>value</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>8064</th>
      <td>ERS017732</td>
      <td>Investigation type</td>
      <td>Metagenome</td>
    </tr>
    <tr>
      <th>8065</th>
      <td>ERS017732</td>
      <td>Project name</td>
      <td>GI tract</td>
    </tr>
    <tr>
      <th>8066</th>
      <td>ERS017732</td>
      <td>Collection date</td>
      <td>2010-03</td>
    </tr>
    <tr>
      <th>8067</th>
      <td>ERS017732</td>
      <td>geographic location (country and/or sea,region)</td>
      <td>USA</td>
    </tr>
    <tr>
      <th>8068</th>
      <td>ERS017732</td>
      <td>geographic location (country and/or sea,region)</td>
      <td>USA</td>
    </tr>
  </tbody>
</table>
</div>




```python

```


```python
#total_samples = samples.groupby("sampleId")
#total_samples.count()
#Rows where there is 1,2,3 null values
samples.dtypes
```




    sampleId     object
    attribute    object
    value        object
    dtype: object



### Add a mapped attribute column 
#### for each row, based on the attribute mappings created before from the xlsx excel file.


```python
def label_mapped_attribute(row):
    attribute = row['attribute']
    if(pd.isnull(attribute)):
        return "NAN"            
    if(attribute in mapping_cell_type):
        return "CellType"
    if(attribute in mapping_sex):
        return "Sex"
    if(attribute in mapping_depth):
        return "Depth"
    if(attribute in mapping_cell_line):
        return "CellLine"
    if(attribute in mapping_collection_date):
        return "CollectionDate"
    if(attribute in mapping_lat_long):
        return "Lat_Long"
    return "NotMapped"
```


```python
samples["mapped_attribute"] = samples.apply(lambda row: label_mapped_attribute(row),axis=1)
samples.head(20)
```




<div>
<style>
    .dataframe thead tr:only-child th {
        text-align: right;
    }

    .dataframe thead th {
        text-align: left;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>sampleId</th>
      <th>attribute</th>
      <th>value</th>
      <th>mapped_attribute</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>ERS008227</td>
      <td>geographic location (country)</td>
      <td>Burkina Faso:Boulkiemde province,Boulpon</td>
      <td>NotMapped</td>
    </tr>
    <tr>
      <th>1</th>
      <td>ERS008227</td>
      <td>geographic location (latitude and longitude)</td>
      <td>2.066667 W 12.65 N</td>
      <td>Lat_Long</td>
    </tr>
    <tr>
      <th>2</th>
      <td>ERS008227</td>
      <td>sex</td>
      <td>female</td>
      <td>Sex</td>
    </tr>
    <tr>
      <th>3</th>
      <td>ERS008228</td>
      <td>geographic location (country)</td>
      <td>Italy:Toscany, Florence</td>
      <td>NotMapped</td>
    </tr>
    <tr>
      <th>4</th>
      <td>ERS008228</td>
      <td>geographic location (latitude and longitude)</td>
      <td>11.25 E 43.783333 N</td>
      <td>Lat_Long</td>
    </tr>
    <tr>
      <th>5</th>
      <td>ERS008228</td>
      <td>sex</td>
      <td>female</td>
      <td>Sex</td>
    </tr>
    <tr>
      <th>6</th>
      <td>ERS000030</td>
      <td>Country</td>
      <td>Czech Republic</td>
      <td>NotMapped</td>
    </tr>
    <tr>
      <th>7</th>
      <td>ERS000042</td>
      <td>Longitude</td>
      <td>-83</td>
      <td>Lat_Long</td>
    </tr>
    <tr>
      <th>8</th>
      <td>ERS000042</td>
      <td>Latitude</td>
      <td>40</td>
      <td>Lat_Long</td>
    </tr>
    <tr>
      <th>9</th>
      <td>ERS000067</td>
      <td>Latitude</td>
      <td>missing</td>
      <td>Lat_Long</td>
    </tr>
    <tr>
      <th>10</th>
      <td>ERS000067</td>
      <td>Longitude</td>
      <td>missing</td>
      <td>Lat_Long</td>
    </tr>
    <tr>
      <th>11</th>
      <td>ERS000065</td>
      <td>Latitude</td>
      <td>36</td>
      <td>Lat_Long</td>
    </tr>
    <tr>
      <th>12</th>
      <td>ERS000065</td>
      <td>Longitude</td>
      <td>-120</td>
      <td>Lat_Long</td>
    </tr>
    <tr>
      <th>13</th>
      <td>ERS000053</td>
      <td>Longitude</td>
      <td>11.0</td>
      <td>Lat_Long</td>
    </tr>
    <tr>
      <th>14</th>
      <td>ERS000053</td>
      <td>Latitude</td>
      <td>49.6</td>
      <td>Lat_Long</td>
    </tr>
    <tr>
      <th>15</th>
      <td>ERS000078</td>
      <td>Longitude</td>
      <td>81</td>
      <td>Lat_Long</td>
    </tr>
    <tr>
      <th>16</th>
      <td>ERS000078</td>
      <td>Latitude</td>
      <td>7.5</td>
      <td>Lat_Long</td>
    </tr>
    <tr>
      <th>17</th>
      <td>ERS000056</td>
      <td>Longitude</td>
      <td>10</td>
      <td>Lat_Long</td>
    </tr>
    <tr>
      <th>18</th>
      <td>ERS000056</td>
      <td>Latitude</td>
      <td>51</td>
      <td>Lat_Long</td>
    </tr>
    <tr>
      <th>19</th>
      <td>ERS000047</td>
      <td>Latitude</td>
      <td>16.4</td>
      <td>Lat_Long</td>
    </tr>
  </tbody>
</table>
</div>




```python
print(samples.groupby(['mapped_attribute']).size())
samples[samples['attribute'].isin(mapping_depth)].count()
```

    mapped_attribute
    CellType             8
    CollectionDate    3045
    Depth               97
    Lat_Long           245
    NAN                  2
    NotMapped         6130
    Sex                476
    dtype: int64
    




    sampleId            97
    attribute           97
    value               95
    mapped_attribute    97
    dtype: int64




```python
# Check if there is a duplicate attribute assignment for a sample
sample_attr_grp = samples.groupby(['sampleId', 'mapped_attribute'])
sample_attr_grp.size().reset_index(name='Freq')
sample_attr_duplicate = sample_attr_grp.filter(lambda x: len(x) > 1)
sample_attr_duplicate[sample_attr_duplicate['mapped_attribute'].isin(['Depth','CollectionDate'])]
```




<div>
<style>
    .dataframe thead tr:only-child th {
        text-align: right;
    }

    .dataframe thead th {
        text-align: left;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>sampleId</th>
      <th>attribute</th>
      <th>value</th>
      <th>mapped_attribute</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>165</th>
      <td>ERS955657</td>
      <td>collection_date</td>
      <td>07-Nov-2012/31-Jan-2013</td>
      <td>CollectionDate</td>
    </tr>
    <tr>
      <th>169</th>
      <td>ERS955657</td>
      <td>collection date</td>
      <td>NaN</td>
      <td>CollectionDate</td>
    </tr>
  </tbody>
</table>
</div>



### Look into the values data


```python
samples_collection_dates = samples[samples.mapped_attribute == "CollectionDate"].sort_values(['value'])
#samples_collection_dates
```


```python
#samples_collection_dates['date']= pd.to_datetime(samples_collection_dates['value'])
samples_collection_dates.sort_values(['value']).groupby(['value']).count()
```




<div>
<style>
    .dataframe thead tr:only-child th {
        text-align: right;
    }

    .dataframe thead th {
        text-align: left;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>sampleId</th>
      <th>attribute</th>
      <th>mapped_attribute</th>
    </tr>
    <tr>
      <th>value</th>
      <th></th>
      <th></th>
      <th></th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>05-Dec-08</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>07-Nov-2012/31-Jan-2013</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>09/09/2004</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>10/08/2001</th>
      <td>2</td>
      <td>2</td>
      <td>2</td>
    </tr>
    <tr>
      <th>11-Nov-06</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>13-Nov-06</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>14/01/1991</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>1800-01-01/2015-01-01</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>1800/2014</th>
      <td>59</td>
      <td>59</td>
      <td>59</td>
    </tr>
    <tr>
      <th>1800/2015</th>
      <td>10</td>
      <td>10</td>
      <td>10</td>
    </tr>
    <tr>
      <th>1800/2016</th>
      <td>2</td>
      <td>2</td>
      <td>2</td>
    </tr>
    <tr>
      <th>1900/2008</th>
      <td>30</td>
      <td>30</td>
      <td>30</td>
    </tr>
    <tr>
      <th>1900/2013</th>
      <td>10</td>
      <td>10</td>
      <td>10</td>
    </tr>
    <tr>
      <th>1900/2014</th>
      <td>5</td>
      <td>5</td>
      <td>5</td>
    </tr>
    <tr>
      <th>1935</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>1937</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>1939</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>1943</th>
      <td>3</td>
      <td>3</td>
      <td>3</td>
    </tr>
    <tr>
      <th>1943-01-01</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>1944-01-01</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>1945</th>
      <td>4</td>
      <td>4</td>
      <td>4</td>
    </tr>
    <tr>
      <th>1945-01-01</th>
      <td>3</td>
      <td>3</td>
      <td>3</td>
    </tr>
    <tr>
      <th>1946</th>
      <td>5</td>
      <td>5</td>
      <td>5</td>
    </tr>
    <tr>
      <th>1949</th>
      <td>2</td>
      <td>2</td>
      <td>2</td>
    </tr>
    <tr>
      <th>1950</th>
      <td>2</td>
      <td>2</td>
      <td>2</td>
    </tr>
    <tr>
      <th>1952</th>
      <td>5</td>
      <td>5</td>
      <td>5</td>
    </tr>
    <tr>
      <th>1956</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>1957</th>
      <td>3</td>
      <td>3</td>
      <td>3</td>
    </tr>
    <tr>
      <th>1958-01-01</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>1959</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>1960</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>1961</th>
      <td>4</td>
      <td>4</td>
      <td>4</td>
    </tr>
    <tr>
      <th>1962</th>
      <td>9</td>
      <td>9</td>
      <td>9</td>
    </tr>
    <tr>
      <th>1963</th>
      <td>2</td>
      <td>2</td>
      <td>2</td>
    </tr>
    <tr>
      <th>1964</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>1965</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>1965-01-01</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>1966</th>
      <td>2</td>
      <td>2</td>
      <td>2</td>
    </tr>
    <tr>
      <th>1966-01-01</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>1967</th>
      <td>4</td>
      <td>4</td>
      <td>4</td>
    </tr>
    <tr>
      <th>1967-01-01</th>
      <td>2</td>
      <td>2</td>
      <td>2</td>
    </tr>
    <tr>
      <th>1968</th>
      <td>3</td>
      <td>3</td>
      <td>3</td>
    </tr>
    <tr>
      <th>1969</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>1970</th>
      <td>3</td>
      <td>3</td>
      <td>3</td>
    </tr>
    <tr>
      <th>1970/1980</th>
      <td>2</td>
      <td>2</td>
      <td>2</td>
    </tr>
    <tr>
      <th>1971</th>
      <td>3</td>
      <td>3</td>
      <td>3</td>
    </tr>
    <tr>
      <th>1973</th>
      <td>2</td>
      <td>2</td>
      <td>2</td>
    </tr>
    <tr>
      <th>1973-01-01</th>
      <td>3</td>
      <td>3</td>
      <td>3</td>
    </tr>
    <tr>
      <th>1974</th>
      <td>4</td>
      <td>4</td>
      <td>4</td>
    </tr>
    <tr>
      <th>1974-01-01</th>
      <td>4</td>
      <td>4</td>
      <td>4</td>
    </tr>
    <tr>
      <th>...</th>
      <td>...</td>
      <td>...</td>
      <td>...</td>
    </tr>
    <tr>
      <th>2010-08-03</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2010-08-04</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2010-08-06</th>
      <td>2</td>
      <td>2</td>
      <td>2</td>
    </tr>
    <tr>
      <th>2010-08-09</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2010-08-10</th>
      <td>2</td>
      <td>2</td>
      <td>2</td>
    </tr>
    <tr>
      <th>2010-08-17</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2010-08-19</th>
      <td>4</td>
      <td>4</td>
      <td>4</td>
    </tr>
    <tr>
      <th>2010-08-20</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2010-08-23</th>
      <td>2</td>
      <td>2</td>
      <td>2</td>
    </tr>
    <tr>
      <th>2010-08-27</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2010-08-29</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2010-08-31</th>
      <td>2</td>
      <td>2</td>
      <td>2</td>
    </tr>
    <tr>
      <th>2010-09-02</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2010-09-04</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2010-09-06</th>
      <td>2</td>
      <td>2</td>
      <td>2</td>
    </tr>
    <tr>
      <th>2010-09-08</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2010-09-09</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2010-09-10</th>
      <td>2</td>
      <td>2</td>
      <td>2</td>
    </tr>
    <tr>
      <th>2010-09-12</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2010-09-13</th>
      <td>3</td>
      <td>3</td>
      <td>3</td>
    </tr>
    <tr>
      <th>2010-09-16</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2010-09-20</th>
      <td>4</td>
      <td>4</td>
      <td>4</td>
    </tr>
    <tr>
      <th>2010-09-21</th>
      <td>2</td>
      <td>2</td>
      <td>2</td>
    </tr>
    <tr>
      <th>2010-09-22</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2010-09-25</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2010-09-27</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2010-10-01</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2010-10-03</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2010-10-04</th>
      <td>2</td>
      <td>2</td>
      <td>2</td>
    </tr>
    <tr>
      <th>2010-10-08</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2010-10-10</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2010-10-11</th>
      <td>2</td>
      <td>2</td>
      <td>2</td>
    </tr>
    <tr>
      <th>2010-10-12</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2010-10-13</th>
      <td>2</td>
      <td>2</td>
      <td>2</td>
    </tr>
    <tr>
      <th>2010-10-14</th>
      <td>3</td>
      <td>3</td>
      <td>3</td>
    </tr>
    <tr>
      <th>2010-10-18</th>
      <td>2</td>
      <td>2</td>
      <td>2</td>
    </tr>
    <tr>
      <th>2010-10-20</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2010-10-25</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2010-10-26</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2010-11-10</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2011</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2012</th>
      <td>16</td>
      <td>16</td>
      <td>16</td>
    </tr>
    <tr>
      <th>2012-05-31</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2013</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2013-07</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2014</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>22/01/2004</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>25/08/1995</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>July 2007</th>
      <td>20</td>
      <td>20</td>
      <td>20</td>
    </tr>
    <tr>
      <th>not available</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
  </tbody>
</table>
<p>802 rows × 3 columns</p>
</div>




```python
#samples_collection_dates.value.notnull() and samples_collection_dates.value.str.contains('(/){1}')
samples_collection_dates_values = samples_collection_dates['value']
samples_collection_dates_values = samples_collection_dates_values.dropna()
samples_collection_dates_values[samples_collection_dates_values.str.match(r'^[^/]+/[^/]+$')].unique()
```




    array(['07-Nov-2012/31-Jan-2013', '1800-01-01/2015-01-01', '1800/2014',
           '1800/2015', '1800/2016', '1900/2008', '1900/2013', '1900/2014',
           '1970/1980', '1987/1989', '1993/1995', '1998/2001'], dtype=object)




```python
samples_depth = samples[samples.mapped_attribute == "Depth"].sort_values(['value'])
#samples_depth
```


```python
samples_depth.groupby(['value']).count()
```




<div>
<style>
    .dataframe thead tr:only-child th {
        text-align: right;
    }

    .dataframe thead th {
        text-align: left;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>sampleId</th>
      <th>attribute</th>
      <th>mapped_attribute</th>
    </tr>
    <tr>
      <th>value</th>
      <th></th>
      <th></th>
      <th></th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>30</td>
      <td>30</td>
      <td>30</td>
    </tr>
    <tr>
      <th>0 meters</th>
      <td>14</td>
      <td>14</td>
      <td>14</td>
    </tr>
    <tr>
      <th>0-15</th>
      <td>20</td>
      <td>20</td>
      <td>20</td>
    </tr>
    <tr>
      <th>0.1</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>1</th>
      <td>8</td>
      <td>8</td>
      <td>8</td>
    </tr>
    <tr>
      <th>1.7 +/- 0.3</th>
      <td>2</td>
      <td>2</td>
      <td>2</td>
    </tr>
    <tr>
      <th>10</th>
      <td>7</td>
      <td>7</td>
      <td>7</td>
    </tr>
    <tr>
      <th>101</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>145</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>20</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>215</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>30</th>
      <td>3</td>
      <td>3</td>
      <td>3</td>
    </tr>
    <tr>
      <th>5</th>
      <td>2</td>
      <td>2</td>
      <td>2</td>
    </tr>
    <tr>
      <th>Neuston</th>
      <td>3</td>
      <td>3</td>
      <td>3</td>
    </tr>
  </tbody>
</table>
</div>




```python

```
