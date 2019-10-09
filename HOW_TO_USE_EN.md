# How to use Ohara Quickstart VM

- [Prerequisites](#prerequisites)
- [Import OVA](#import-ova)
- [Add VirtualBox network card](#add-virtualbox-network-card)
- [Login OharaStream](#login-oharastream)
  - [Ohara Component](#ohara-component)
- [Open Ohara Manager](#open-ohara-manager)
- [Add a node](#add-a-node)
- [Create a workspace](#create-a-workspace)
- [Create Two Topics](#create-two-topics)
- [Upload a stream Jar](#upload-a-stream-jar)
  - [How to download jar?](https://github.com/oharastream/ohara-quickstart/releases/download/untagged-cf0d3622e4d0954dcb7a/ohara-streamapp.jar)
- [Add a pipeline](#add-a-pipeline)
  - [Add a JDBC source connector](#add-a-jdbc-source-connector)
  - [Add Two Topics](#add-two-topics)
  - [Add a stream APP](#add-a-stream-app)
  - [Add FTP sink connector](#add-ftp-sink-connector)
- [Use Pipeline Graph](#use-pipeline-graph)
  - [Setting jdbc connector](#setting-jdbc-connector)
  - [Setting StreamApp](#setting-streamapp)
  - [Setting FTP Sink Connector](#setting-ftp-sink-connector)
  - [Start Pipeline](#start-pipeline)
  - [Check Output file](#check-output-file)

## Prerequisites

- [VirtualBox 5.2+](https://www.virtualbox.org/): ohara-quickstart only supports Version 5.2 or above of VirualBox
- [Add Virtual Box network card](#add-virtualbox-network-card): ohara-quickstart requires a network card to enable VM to properly connect to LAN computers

## Import OVA

- You can use VirtualBox user interface to import the OVA file(File -> Import Appliance)

## Add VirtualBox network card

### Windows user

1. Create new network card
2. Setting network card in vm

### Create new network card

- Click Global Tools and then click Create, ensure that the DHCP option is selected to see that the network card has been set up in Windows.

![create_network_windows](images/win_add_network.png)

### Setting network card in vm

- Select the imported ohara-quickstart, click Setting, click Network, click adapter2, select Host-only Adapter, and select the newly added network card.

![setting_network_windows](images/win_setting_network.png)

### Mac and Linux user

1. Create new network card
2. Setting network card in vm

### Create new network card

- Click Tools and then click Create, ensure that the DHCP option is selected to see that the network card has been set up in mac or linux.

![create_network_mac](images/mac_add_network.png)

### Setting network card in vm

- Select the imported ohara-quickstart, click Setting, click Network, click adapter2, select Host-only Adapter, and select the newly added network card.

![setting_network_mac](images/mac_setting_network.png)

## Login Ohara Quickstart VM

- The default account and password are **ohara** and **oharastream**

```
Welcome to Ubuntu 18.04.3 LTS (GNU/Linux 4.15.0-55-generic x86_64)

 * Documentation:  https://help.ubuntu.com
 * Management:     https://landscape.canonical.com
 * Support:        https://ubuntu.com/advantage

HOST=192.168.56.104
OHARA_VER=0.8.0-SNAPSHOT
```

- After logging in, you can get relevant information and wait for the necessary environment settings to be established.

```
> FTP ready on ftp://ohara:oharastream@192.168.56.104:21

> Postgresql ready on jdbc:postgresql://192.168.56.104:5432/postgres (user=ohara, password=oharastream)

> SMB ready on smb://ohara:oharastream@192.168.56.104:445/ohara

> Ohara ready on http://192.168.56.104:5050
```

### Ohara Component

- [Node](https://ohara.readthedocs.io/en/latest/rest/rest-nodes.html): Node is the basic unit of running service. It can be either physical machine or vm.

- [Workspace](https://ohara.readthedocs.io/en/latest/user_guide.html#workspaces): A workspace contains multiple Ohara services including: Zookeepers, Brokers and Workers. You can create a workspace and add new node, topic and stream application in these pages.
- [Pipeline](https://ohara.readthedocs.io/en/latest/rest/rest-pipelines.html#pipeline): Pipeline APIs are born of Ohara manager which needs a way to store the relationship of components in streaming.
- [Connector](https://ohara.readthedocs.io/en/latest/rest/rest-connectors.html): Connector is core of application in ohara pipeline. Connector has two type - source and sink. Source connector pulls data from another system and then push to topic.
- [StreamApp](https://ohara.readthedocs.io/en/latest/custom_streamapp.html#streamapp): Ohara StreamApp is a unparalleled wrap of kafka streaming. It leverages and enhances Kafka Streams to make developer easily design and implement the streaming application.

## Open Ohara Manager

- Open Ohara Manager with a browser (URL:192.168.56.104:5050)

## Add a node

- Click Menu **Nodes**
- Click **NEW NODE**

![node_list](images/node_list.png)

- Type Node (\${IP})
- Type Port **22**
- Type User **ohara**
- Type Password **oharastream**
- Click **TEST CONNECTION**
- Click **ADD**

![new_node](images/new_node.png)

- After the node was added, you can see the newly created node on the Nodes page.

![node_list](images/new_node_list.png)

## Create a workspace

- Click Menu **Workspaces**
- Click **NEW WORKSPACE**

![workspace_list](images/workspace_list.png)

**Posgresql is needed in the subsequent process, so we need to upload it to workspace first. ([How to download postgresql drive jar?](https://jdbc.postgresql.org/download.html))**

- Type name **wk00**
- Select Node **${HOST} or ${IP}**
- Click **NEW PLUGIN**
- Upload file **postgresql-1.jdbc.jar**
- Select **postgresql-1.jdbc.jar**
- Click **ADD**

![new_workspace](images/new_workspace.png)

- And wait for the setup to complete.

![wait_workspace](images/wait_workspace.png)

- You can see that workspace has been built on the list

![new_workspace_list](images/new_workspace_list.png)

## Create Two Topics

- Click Menu **Workspaces**
- Click **wk00 Action**
- Click **TOPICS**

![topic_list](images/topic_list.png)

- Click **NEW TOPIC**
- Type Topic name **topic00**
- Type patitions **1**
- Type Repliction factor **1**
- Click **ADD**

![new_topic00](images/new_topic00.png)

- Click **NEW TOPIC**
- Type Topic name **topic01**
- Type patitions **1**
- Type Repliction factor **1**
- Click **ADD**

![new_topic01](images/new_topic01.png)

- You can see two topices to create the completion in the list

![new_topic_list2](images/new_topic_list2.png)

## Upload a stream Jar

**[StreamApp](https://ohara.readthedocs.io/en/latest/rest/rest-stream.html) has filter functionality in the next pipeline process([How to Write StreamApp with Filter Function](https://github.com/oharastream/ohara-quickstart/blob/master/stream-app/FilterStreamApp.java))**

This is [jar](https://github.com/oharastream/ohara-quickstart/releases/download/untagged-cf0d3622e4d0954dcb7a/ohara-streamapp.jar) we finished beforehand.

- Click **STREAM JARS**
- Click **NEW JAR**
- Upload file **ohara-streamapp.jar**

![stream_list](images/stream_list.png)

## Add a pipeline

- Click Menu **Piplines**
- Click **NEW PIPELINE**

![pipeline_list](images/pipeline_list.png)

- Type Pipeline name **pipeline**
- Select Workspace name **wk00**
- Click **ADD**

![new_pipeline](images/new_pipeline.png)
![edit_pipeline](images/edit_pipeline.png)

### Add a JDBC source connector

- Click source connector icon
- Select **com.island.ohara.connector.jdbc.source.JDBCSourceConnector**
- Click **ADD**

![new_source_connector](images/new_source_connector.png)

- Type name **jdbc**
- Click **ADD**

![new_source_connector_name](images/new_source_connector_name.png)

### Add Two Topics

- Click topic icon
- Select **topic00**
- Click **ADD**

![new_topic](images/new_topic.png)

- Click topic icon
- Select **topic01**
- Click **ADD**

![new_topic2](images/new_topic2.png)

### Add a stream APP

- Click stream app icon
- Select **ohara-streamapp.jar**

![new_stream](images/new_stream.png)

- Type name **stream**
- Click **ADD**

![new_stream_name](images/new_stream_name.png)

### Add FTP sink connector

- Click sink connector icon
- Select **com.island.ohara.connector.ftp.FtpSink**
- Click **ADD**

![new_sink_connector](images/new_sink_connector.png)

- Type name **ftp**
- Click **ADD**

![new_sink_connector_name](images/new_sink_connector_name.png)

## Use Pipeline Graph

### Setting jdbc connector

- Select jdbc connector
- Type jdbc url [**\${POSTGRESQL_URL}**](#login-oharastream)
- Type user name **ohara**
- Type password **oharastream**
- Type table name **employees**
- Type timestamp column name **create_at**

![setting_jdbc_common](images/setting_jdbc_common.png)

- Click CORE tabs
- Select Topics **topic00**
- Click **TEST YOUR CONFIGS**
- Verify that the settings are correct

![setting_jdbc_core](images/setting_jdbc_core.png)

### Setting StreamApp

- Select streamapp
- Type column name **employee_id,first_name**

![setting_stream_common](images/setting_stream_common.png)

- Click CORE tabs
- Select From topic of data consuming from **topic00**
- Select To topic of data produce to **topic01**
- Type Instances **1**
- Verify that the settings are correct

![setting_stream_core](images/setting_stream_core.png)

### Setting FTP Sink Connector

- Select ftp connector
- Type Output folder **output**
- File Need Header **enabled**
- Type Host [**\${FTP_HOST}**](#login-oharastream)
- Type Port [**\${FTP_PORT}**](#login-oharastream)
- Type User **ohara**
- Type Password **oharastream**

![setting_sink_connector_common](images/setting_sink_connector_common.png)

- Click CORE tabs
- Select Topics **topic01**
- Click **TEST YOUR CONFIGS**
- Verify that the settings are correct

![setting_sink_connector_core](images/setting_sink_connector_core.png)

### Start Pipeline

- Click Operate **START_ICON**

![start_graph](images/start_graph.png)

- Wait a minute and you can see that all connectors on the graph turn green and metrics are displayed to indicate that the service started successfully.

### Check Output file

- Enter the URL in browser's address bar [**\${FTP_URL}**](#login-oharastream)

![ftp_url](images/ftp_url.png)
![ftp_output_url](images/ftp_output_url.png)
![ftp_partition_url](images/ftp_partition_url.png)
![ftp_csv_url](images/ftp_csv_url.png)

- You can see that the output table has already filtered two fields, **employee_id** and **first_name**.
