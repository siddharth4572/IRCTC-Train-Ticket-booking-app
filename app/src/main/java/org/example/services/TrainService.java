package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entities.Train;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TrainService {
    private List<Train> trainList;
    private ObjectMapper objectMapper=new ObjectMapper();
    private static final String Train_path="app/src/main/java/org/example/localDb/trains.json";
    public TrainService() throws IOException{
        File trains=new File(Train_path);
        trainList=objectMapper.readValue(trains, new TypeReference<List<Train>>() {});

    }
    public List<Train> searchTrains(String source ,String destination){
        return trainList.stream().filter(train->validTrain(train,source,destination)).collect(Collectors.toList());
    }
    public void addTrain(Train newTrain){
        Optional<Train> existingTrain = trainList.stream().filter(train->train.getTrainId().equalsIgnoreCase(newTrain.getTrainId())).findFirst();

        if(existingTrain.isPresent()){
            updateTrain(newTrain);

        }
        else{
            trainList.add(newTrain);
            saveTrainListToFile();
        }

    }
    public void updateTrain(Train updatedTrain) {
        OptionalInt index = IntStream.range(0, trainList.size()).filter(i -> trainList.get(i).getTrainId().equalsIgnoreCase(updatedTrain.getTrainId())).findFirst();

        if (index.isPresent()) {
            trainList.set(index.getAsInt(), updatedTrain);
            saveTrainListToFile();

        } else {
            addTrain(updatedTrain);

        }
    }

    private void saveTrainListToFile(){
        try{
            File trainFile = new File(Train_path);
            // Create parent directories if they don't exist
            File parentDir = trainFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            objectMapper.writeValue(trainFile, trainList);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private boolean validTrain(Train train,String source ,String destination){
        List<String> stationOrder= train.getStations();
        int sourceIndex= stationOrder.indexOf(source.toLowerCase());
        int destinationIndex= stationOrder.indexOf(destination.toLowerCase());

        return sourceIndex!=-1 && destinationIndex!=-1 && sourceIndex<destinationIndex;
    }

}
