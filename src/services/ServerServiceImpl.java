package services;


import java.util.List;


import models.ServerModel;
import models.ServerModelON;

import org.opennebula.client.Client;
import org.opennebula.client.OneResponse;
import org.opennebula.client.host.Host;

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
		// TODO Auto-generated method stub
		return null;
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