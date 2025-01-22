from collections import defaultdict
from typing import Dict, Tuple
import argparse
import csv

PROTOCOLS_MAP: Dict[int, str] = {}
LOOKUP_TABLE_MAP: Dict[Tuple[int, str], str] = {}

# Minimum expected fields in each log entry (based on the log format specification)
MIN_FIELDS_IN_LOG_V2 = 14

def get_arguments():
    parser = argparse.ArgumentParser(description="Parse flow logs and generate output report.")
    parser.add_argument("--lookup", default="lookup.csv", help="Path to lookup table CSV")
    parser.add_argument("--protocols", default="protocol-numbers.csv", help="Path to protocol numbers CSV")
    parser.add_argument("--logs", default="flow-logs.txt", help="Path to flow logs")
    parser.add_argument("--output", default="output.txt", help="Output file path")
    return parser.parse_args()

def load_protocol_numbers(protocols_file_path: str) -> None:
  try:
    with open(protocols_file_path, 'r') as f:
      reader = csv.DictReader(f)
      for row in reader:
        PROTOCOLS_MAP[int(row['Decimal'])] = row["Keyword"].lower()
  except Exception as e:
    print(f"An error occurred while reading protocol numbers: {e}")
    raise

def load_lookup_table(lookup_file_path: str) -> None:
  try:
    with open(lookup_file_path, 'r') as f:
      reader = csv.DictReader(f)
      for row in reader:
        LOOKUP_TABLE_MAP[(int(row['dstport']),row['protocol'].lower())] = row['tag']
  except Exception as e:
    print(f"An error occurred while reading lookup table: {e}")
    raise

def parse_logs(flow_logs_file_path:str) -> Tuple[Dict[str, int], Dict[Tuple[int, str], int]]:
  tag_counts = defaultdict(int)
  port_protocol_counts = defaultdict(int)
  try:
    with open(flow_logs_file_path, 'r') as f:
      for line in f:
        keys = line.strip().split()
        if not keys or len(keys) < MIN_FIELDS_IN_LOG_V2:
          print(f"Error reading the log (not enough fields or invalid format). Skipping record")
          print(line)
          continue
        try: 
          dstport = int(keys[6])
          protocol = PROTOCOLS_MAP.get(int(keys[7]), 'unknown').lower()
        except ValueError:
          print(f"Invalid port or protocol in line: {line.strip()} - Error: {e}")
          continue
        tag = LOOKUP_TABLE_MAP.get((dstport, protocol), 'untagged')
        tag_counts[tag] += 1
        port_protocol_counts[(dstport, protocol)] += 1
  except Exception as e:
    print(f"An error occurred while parsing logs: {e}")
    raise
  return (tag_counts, port_protocol_counts)

def write_output(output_file_path:str, tag_counts: Dict[str, int], port_protocol_counts: Dict[Tuple[int, str], int])-> None:
  try:
    with open(output_file_path, 'w') as f:
        f.write("Tag Counts:\n")
        f.write("Tag,Count\n")
        for tag, count in tag_counts.items():
            f.write(f"{tag},{count}\n")

        f.write("\nPort/Protocol Combination Counts:\n")
        f.write("Port,Protocol,Count\n")
        for (port, protocol), count in port_protocol_counts.items():
            f.write(f"{port},{protocol},{count}\n")
  except Exception as e:
    print(f"An error occurred while write to output file: {e}")
    raise

def main():
  args = get_arguments()
  load_protocol_numbers(args.protocols)
  load_lookup_table(args.lookup)
  tag_counts,port_protocol_counts = parse_logs(args.logs)
  write_output(args.output, tag_counts, port_protocol_counts)

if __name__ == '__main__':
  main()