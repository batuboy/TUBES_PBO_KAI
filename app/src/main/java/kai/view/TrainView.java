package kai.view;

import kai.controller.TrainController;
import kai.models.train.Locomotive;


public class TrainView {
    private TrainController trainController;
    private Locomotive locomotive;
    
    public TrainView() {
        trainController = new TrainController();
    }
    
    
    public void displayAllLocomotive () {
        trainController.getAllLocomotives().forEach(train -> {
            System.out.println("Train ID: " + train.getLocomotiveId() + ", Name: " + train.getName() +
                    ", Type: " + train.getLocomotiveType() + ", Status: " + train.getStatus());
        });
    }

    
}
