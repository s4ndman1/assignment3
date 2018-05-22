package za.ac.university.pretoria.node.mvc.view;

import za.ac.university.pretoria.node.api.NodeUI;
import za.ac.university.pretoria.node.mvc.model.NodeException;
import za.ac.university.pretoria.node.mvc.model.NodeInfo;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Path("/node")
public class NodeRestApi {

    private NodeUI nodeUI;

    public NodeRestApi() {
    }

    @Inject
    public void setNodeUI(NodeUI nodeUI) {
        this.nodeUI = nodeUI;
    }

    @GET
    @Path("/getAllNodes")
    @Produces(MediaType.APPLICATION_JSON)
    public List<NodeInfo> getAllNodes() throws SQLException {
        return nodeUI.viewAllNodes();
    }


    @GET
    @Path("/getAllOwnNodes/{adminID}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<NodeInfo> getAllOwnNodes(@PathParam("adminID") String adminID) throws SQLException {
        return nodeUI.viewAllOwnNodes(adminID);
    }

    @POST
    @Path("/approveNode/{adminID}/{timeStart}/{timeEnd}")
    public boolean approveNode(@PathParam("adminID")String adminID, @PathParam("timeStart")String timeStart, @PathParam("timeEnd")String timeEnd) throws SQLException, NodeException {
        DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startTime = LocalTime.from(TIME_FORMATTER.parse(timeStart));
        LocalTime endTime =  LocalTime.from(TIME_FORMATTER.parse(timeEnd));

        nodeUI.approveNode(adminID,startTime,endTime);
        return true;
    }


    @POST
    @Path("/changeNodeToActive/{nodeID}")
    public boolean changeNodeToActive(@PathParam("nodeID")String nodeID) throws SQLException {

        nodeUI.changeNodeToActive(nodeID);
        return true;
    }


    @POST
    @Path("/killNode/{nodeID}")
    public boolean killNode(@PathParam("nodeID")String nodeID) throws SQLException {

        nodeUI.killNode(nodeID);
        return true;
    }



    @POST
    @Path("/removeNode/{nodeID}")
    public boolean removeNode(@PathParam("nodeID")String nodeID) throws SQLException {

        nodeUI.removeNode(nodeID);
        return true;
    }

}
