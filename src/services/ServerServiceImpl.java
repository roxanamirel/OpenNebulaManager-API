package services;


import java.util.ArrayList;
import java.util.List;



import models.ServerModel;
import models.ServerModelON;

import org.opennebula.client.Client;
import org.opennebula.client.OneResponse;
import org.opennebula.client.host.Host;
import org.opennebula.client.host.HostPool;

import config.Configurations;

import os.ServerOperations;
import parsers.OpenNebulaInfoXMLParser;
import responsehelper.ResponseHelper;
import client.OpenNebulaClient;
import exceptions.ServiceCenterAccessException;
import util.Energy;
import util.ResponseMessage;

/**
 * 
 * @author AM
 */
public class ServerServiceImpl extends ServerService {

	@Override
	public ResponseMessage enable(ServerModel server) {
		Client client = OpenNebulaClient.getInstance();
		Host host = new Host(server.getId(), client);
		OneResponse oneResponse = host.enable();
		return ResponseHelper.createResponseMessage(oneResponse);
	}

	@Override
	public ResponseMessage disable(ServerModel server) {
		Client client = OpenNebulaClient.getInstance();
		Host host = new Host(server.getId(), client);
		OneResponse oneResponse = host.disable();
		return ResponseHelper.createResponseMessage(oneResponse);
	}

	

	@Override
	public Energy getEnergyConsumption(ServerModel server) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServerModel getById(int id) throws ServiceCenterAccessException {
		 Client client = OpenNebulaClient.getInstance();
	       
	        Host host = new Host(id, client);

	        OneResponse response = host.info();
	        if (response.isError()) {
	            throw new ServiceCenterAccessException(response.getErrorMessage());
	        }
	        ServerModelON serverModelON = null;
	        while (serverModelON == null || serverModelON.getTotalMem() == 0) {
	            try {
	                response = host.info();
	                serverModelON = OpenNebulaInfoXMLParser.parseServerInfo(response.getMessage());
	                serverModelON.setMacAddress(ServerOperations.getServerMAC(host.getName()));
	            } catch (Exception e) {
	                throw new ServiceCenterAccessException(e.getMessage(), e.getCause());
	            }
	        }
	        return serverModelON;
	}

	@Override
	public List<ServerModel> getAll() {
		List<ServerModelON> serverInfos = new ArrayList<ServerModelON>();

        Client client = null;
        try {
            client = new Client(Configurations.NEBULA_CREDENTIALS, Configurations.NEBULA_RCP_ADDRESS);
        } catch (Exception e) {
            throw new ServiceCenterAccessException(e.getMessage(), e.getCause());
        }

        HostPool hostPool = new HostPool(client);
        hostPool.info();
        Iterator<Host> hostIterator = hostPool.iterator();
        try {
            while (hostIterator.hasNext()) {
                Host host = hostIterator.next();
                try {
                    OneResponse response = host.info();
                    String data = response.getMessage();

                    ServerInfo serverInfo = OpenNebulaInfoXMLParser.parseServerInfo(data);
                    String hostname = serverInfo.getHostName();
                    if (arpTableManager.hasMAC(hostname)) {
                        serverInfo.setMacAddress(arpTableManager.getMAC(hostname));
                    } else {
                        String mac = getServerMAC(hostname);
                        arpTableManager.addMAC(hostname, mac);
                        serverInfo.setMacAddress(mac);
                    }

                    //read CPU Frequency
                    //try to read trough ssh the output of cpufreq-info -l : gives CPU frequency hardware limits
                    //if no reponse, read CPU freq from OpenNebula
                    {
                        try {

                            String cmd = "/usr/bin/ssh -t -t Node1 cpufreq-info -l";

                            //execute the wakeonlan command
                            Process proc = Runtime.getRuntime().exec(cmd);
                            OutputStream outputStream = proc.getOutputStream();
                            InputStream stdin = proc.getInputStream();
                            InputStreamReader isr = new InputStreamReader(stdin);

                            //log the wakeonlan output
                            BufferedReader br = new BufferedReader(isr);
                            String line = br.readLine();
                            if (line != null) {
                                //  ApplicationLoggingSystem.getInstance().LogInfo(line);
                                String[] values = line.split(" ");
                                String cpuFreq = values[values.length - 1];
                                if (cpuFreq.matches("\\d+")) {
                                    serverInfo.setCpuFrequency(Float.parseFloat(cpuFreq));
                                }
                            }

                            br.close();
                            isr.close();
                            stdin.close();
                            outputStream.close();
                            proc.getInputStream().close();
                            proc.getOutputStream().close();
                            proc.getErrorStream().close();
                            proc.destroy();


                        } catch (Exception ex) {
                            ApplicationLoggingSystem.getInstance().LogInfo(ex.getMessage());
                        }
                    }

                    serverInfos.add(serverInfo);
                } catch (Exception e) {
                    ApplicationLoggingSystem.getInstance().LogInfo("Error monitoring " + host.getId() + ". Probably is offline. Because incomplete data about the server was recorded by OpenNebula, it will be IGNORED.");
                }
            }

        } catch (Exception e) {
            throw new ServiceCenterAccessException(e.getMessage(), e.getCause());
        } finally {
            arpTableManager.writeArpTable();
        }

        return serverInfos;
	}

	@Override
	public ResponseMessage delete(ServerModel t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(ServerModel t) {
		// TODO Auto-generated method stub
		return false;
	}

}