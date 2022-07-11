package nextstep.subway.applicaion;

import nextstep.subway.applicaion.dto.StationRequest;
import nextstep.subway.applicaion.dto.StationResponse;
import nextstep.subway.domain.Station;
import nextstep.subway.domain.StationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class StationService {
    private StationRepository stationRepository;

    public StationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    @Transactional
    public StationResponse saveStation(StationRequest stationRequest) {
        Station station = stationRepository.save(new Station(stationRequest.getName()));
        return StationResponse.of(station);
    }

    public List<StationResponse> findAllStations() {
        return stationRepository.findAll().stream()
                .map(StationResponse::of)
                .collect(Collectors.toList());
    }

    public List<StationResponse> findStationsById(List<Long> idList) {
        List<Station> stations = stationRepository.findAllById(idList);
        Assert.isTrue(idList.size() == stations.size(),
                String.format("존재하지 않는 역이 있습니다. idList : %s", idList));
        return stations.stream().map(StationResponse::of).collect(Collectors.toList());
    }

    @Transactional
    public void deleteStationById(Long id) {
        stationRepository.deleteById(id);
    }
}
