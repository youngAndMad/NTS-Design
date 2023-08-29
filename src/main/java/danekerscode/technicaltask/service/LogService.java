package danekerscode.technicaltask.service;


import danekerscode.technicaltask.dto.LogCommandDTO;
import danekerscode.technicaltask.model.Log;

import java.util.List;

public interface LogService {

    void add(LogCommandDTO dto, Long userId);

    List<Log> findUserLogs(Long userId);
}
