package services;

import model.Tap;

import java.util.List;

public interface InputReader {
    public List<Tap> getTaps(String inputFilePath);
}