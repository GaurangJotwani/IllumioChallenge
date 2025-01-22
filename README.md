Hey there! Thanks for checking out my flow log parsing script. This script helps analyze AWS VPC flow logs and maps them with protocol numbers and custom lookup tables. The script processes log data using Python dictionaries to store protocol mappings and lookup tables efficiently. It reads log files in .txt format, protocol mappings from .csv files, and lookup tables from .csv files. It then extracts relevant fields and matches them against preloaded data to generate an output file in .txt format containing Tag Counts and Port/Protocol Combination Counts. I'm open to any changes, improvements, or suggestions you might have! I’d love to hear your thoughts and ways I can make it better.

### Assumptions

Before diving in, here are a couple of assumptions I made while building this script:

1. **Flow Logs Format:** I'm working with **AWS VPC flow logs (Version 2)**, which include a minimum of **14 fields**, as described in the AWS documentation:  
   [AWS VPC Flow Logs Documentation](https://docs.aws.amazon.com/vpc/latest/userguide/flow-log-records.html)

2. **Protocol Numbers:** For protocol mapping, I'm using the standard IANA protocol numbers list from:  
   [IANA Protocol Numbers](https://www.iana.org/assignments/protocol-numbers/protocol-numbers.xhtml)

### How to Run the Script

Running the script is super simple. Just make sure you have Python installed and run the following command:

```bash
python3 LogFileParser.py
```

By **default**, the script assumes the following file paths:

Flow logs file: flow-logs.txt,
Protocol numbers file: protocol-numbers.csv,
Lookup table file: lookup.csv,
Output file: output.txt

If these file paths work for you, awesome! Just run the script as-is. But if you need to customize them, no problem!

### Customizing File Paths

You can provide custom file paths via command-line arguments. Here’s how:

```bash
python3 LogFileParser.py --lookup lookup.csv --protocols protocol-numbers.csv --logs flow-logs.txt --output my_output.txt
```

### Here's what each argument does:

- `--lookup` : Path to the lookup table file.
- `--protocols` : Path to the protocol numbers file.
- `--logs` : Path to the AWS VPC flow logs file.
- `--output` : Path to save the processed output.

Enjoy! Hit me up with any feedback you have.

_Made with <3 By Gaurang_
