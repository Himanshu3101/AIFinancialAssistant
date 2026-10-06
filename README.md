# AI Financial Assistant

Necessary Configurations

- For Ollama Run In Local

ollama --version

ollama pull qwen3:1.7b

ollama run qwen3:1.7b

- For setting-up envirmonemnt

Advanced → Environment Variables
Under "User variables for <your user>" click New...

Enter:
Variable name: OLLAMA_HOST
Variable value: 0.0.0.0:11434

Click OK → OK → OK.

This is the exact type of configuration Ollama recommends on Windows.

- For Network Configuration
  
PC IP - 192.168.1.44 - ipconfig

Phone Ip - 192.168.1.39:5555     -> adb pair IP:PORT (e.g., adb pair 192.168.1.5:43211  ->  adb connect IP:PORT

Ollama IP for device - http://192.168.1.44:11434/api/tags  [YOUR_PC_IP]

--------------------------trobuleshoot---------------------------


D:\Android\Sdk\platform-tools>adb connect 192.168.1.39:5555
cannot connect to 192.168.1.39:5555: No connection could be made because the target machine actively refused it. (10061)

D:\Android\Sdk\platform-tools>adb kill-server

D:\Android\Sdk\platform-tools>adb start-server

D:\Android\Sdk\platform-tools>adb connect 192.168.1.39:5555
cannot connect to 192.168.1.39:5555: No connection could be made because the target machine actively refused it. (10061)

D:\Android\Sdk\platform-tools>adb tcpip 5555
restarting in TCP mode port: 5555

D:\Android\Sdk\platform-tools>adb connect 192.168.1.39:5555
connected to 192.168.1.39:5555

D:\Android\Sdk\platform-tools>adb devices
List of devices attached
192.168.1.39:5555       device

## Overview

## Architecture

## Tech Stack

## Features

## GenAI Architecture

## RAG Flow

## Security

## Testing

## CI/CD

## Future Improvements
