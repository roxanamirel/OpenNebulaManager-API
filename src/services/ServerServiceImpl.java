package services;


import java.util.List;


import models.ServerModel;

import org.opennebula.client.Client;
import org.opennebula.client.OneResponse;
import org.opennebula.client.host.Host;
import responsehelper.ResponseHelper;
import client.OpenNebulaClient;
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
	public ServerModel getById(int id) {
		 Client client = OpenNebulaClient.getInstance();
	       
	        Host host = new Host(physicalHost.getId(), client);

	        OneResponse response = host.info();
	        if (response.isError()) {
	            throw new ServiceCenterAccessException(response.getErrorMessage());
	        }
	        ServerInfo dto = null;
	        while (dto == null || dto.getTotalMem() == 0) {
	            if (SHUTDOWN_REQUESTED) {
	                return new ServerInfo();
	            }
	            try {
	                response = host.info();
	                dto = OpenNebulaInfoXMLParser.parseServerInfo(response.getMessage());
	                dto.setMacAddress(getServerMAC(physicalHost.getHostname()));
	            } catch (Exception e) {
	                throw new ServiceCenterAccessException(e.getMessage(), e.getCause());
	            }
	        }
	        return dto;
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