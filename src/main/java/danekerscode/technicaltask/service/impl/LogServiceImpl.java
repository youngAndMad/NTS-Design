package danekerscode.technicaltask.service.impl;

import danekerscode.technicaltask.dto.LogCommandDTO;
import danekerscode.technicaltask.mapper.LogMapper;
import danekerscode.technicaltask.model.Log;
import danekerscode.technicaltask.repository.LogRepository;
import danekerscode.technicaltask.service.LogService;
import danekerscode.technicaltask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;
    private final LogMapper logMapper;
    private final UserService userService;


    @Override
    public void add(LogCommandDTO dto) {
        var owner = userService.findById(dto.userId());

        var model = logMapper.toModel(dto.content(), owner);

        logRepository.save(model);
    }

    @Override
    public List<Log> findUserLogs(Long userId) {
        return logRepository.findAllByOwnerId(userId);
    }

}
