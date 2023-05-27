package com.example.warehouseassistant.DataBase;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.warehouseassistant.DataModel.OnPiecesLoadedListener;
import com.example.warehouseassistant.DataModel.Order;
import com.example.warehouseassistant.DataModel.OrderAdapter;
import com.example.warehouseassistant.DataModel.Piece;
import com.example.warehouseassistant.DataModel.PieceAdapter;
import com.example.warehouseassistant.DataModel.ReloadRequests;
import com.example.warehouseassistant.DataModel.ReloadRequestsAdapter;
import com.example.warehouseassistant.DataModel.Transport;
import com.example.warehouseassistant.DataModel.User;
import com.example.warehouseassistant.DataModel.UserAdapter;
import com.example.warehouseassistant.UserInitialization.Input;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public class DataBase {

    public void LoadOrders(List<Order> orderList, OrderAdapter orderAdapter){
        GetOrdersTask task = new GetOrdersTask(orderList, orderAdapter);
        task.execute();
    }

    public void LoadTC(List<String> TCList , Spinner spinner, Context context, String selectedTC){
        GetTCTask task = new GetTCTask(TCList, spinner, context, selectedTC);
        task.execute();
    }

    public void LoadPices(List<Piece> picesList, String orderId, String positionId, PieceAdapter adapter, ListView lvPieces, Context context, OnPiecesLoadedListener listener, Boolean isAlert){
        GetPiecesTask task = new GetPiecesTask(picesList, orderId, positionId, adapter, lvPieces, context, listener, isAlert);
        task.execute();
    }

    public void LoadRequest(String idUser, List<Piece> pieces, String TC, String requestType, String ceh, String selectedSklad, Context context){
        LoadRequestTask task = new LoadRequestTask(idUser, pieces, TC, requestType, ceh, selectedSklad, context);
        task.execute();
    }

    public void LoadReloadRequests(List<ReloadRequests> requestsList, ReloadRequestsAdapter reqAdapter){
        GetReloadRequestsTask task = new GetReloadRequestsTask(requestsList, reqAdapter);
        task.execute();
    }

    public void UpdateLoadRequest(String idUser, String status, String requestId, String statusUnload, Context context){
        UpdateLoadRequest task = new UpdateLoadRequest(idUser, status, requestId, statusUnload, context);
        task.execute();
    }

    public void GetUser(List<User> userList, UserAdapter userAdapter){
        GetUserTask task = new GetUserTask(userList, userAdapter);
        task.execute();
    }

    public void DeleteUser(String idUser, String id, Context context){
        DeleteUser task = new DeleteUser(idUser, id, context);
        task.execute();
    }

    public void EditUser(String password, String login, String role, String fullName, String department, String id, String idUser, Context func){
        EditUsertask task = new EditUsertask(password, login, role, fullName, department, id, idUser, func);
        task.execute();
    }

    public void InsertUser(String password, String login, String role, String fullName, String department, Context func, String idUser) throws NoSuchAlgorithmException {
        Input input = new Input();
        input.setPassword(password);
        String pwd = input.getPassword();

        InsertUserTask task = new InsertUserTask(pwd, login, role, fullName, department, func, idUser);
        task.execute();
    }

    public void EditLoadRequest(String ceh, String sklad, String requestId, String idUser, String type, Context context){
        EditLoadRequestTask task = new EditLoadRequestTask(ceh, sklad, requestId, idUser, type, context);
        task.execute();
    }

    public void GetPiecesForIsolation(List<Piece> picesList, PieceAdapter adapter, Context context){
        GetPiecesForIsolation task = new GetPiecesForIsolation(picesList, adapter, context);
        task.execute();
    }

    public void CreateIsolation(String pieceId, String idUser, String reason, Context context){
        CreateIsolationTask task = new CreateIsolationTask(pieceId, idUser, reason, context);
        task.execute();
    }

}
