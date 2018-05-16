package za.ac.university.pretoria.node.mvc.view;

import za.ac.university.pretoria.node.mvc.model.NodeException;
import za.ac.university.pretoria.node.mvc.model.NodeInfo;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

@Path("/node")
public class NodeRestApi {

    NodeUIImpl nodeUI;

    @Inject
    public NodeRestApi(NodeUIImpl nodeUI) throws SQLException, ClassNotFoundException {
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

        LocalTime startTime = LocalTime.parse(timeStart);
        LocalTime endTime = LocalTime.parse(timeEnd);

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
