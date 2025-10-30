package ru.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.dto.category.CategoryDto;
import ru.practicum.dto.category.NewCategoryDto;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.model.Category;
import ru.practicum.model.Event;
import ru.practicum.model.mappers.CategoryMapper;
import ru.practicum.repository.CategoryRepository;
import ru.practicum.repository.EventRepository;
import ru.practicum.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventsRepository;

    @Override
    public CategoryDto addCategory(NewCategoryDto request) {
        checkUniqueNameCategoryIgnoreCase(request.getName());
        Category category = CategoryMapper.mapFromNewRequest(request);
        Category saveCategory = categoryRepository.save(category);
        log.debug("Добавлена категория {}", saveCategory);
        return CategoryMapper.mapToDto(saveCategory);
    }

    @Override
    public void deleteCategory(Long catId) {
        Category category = checkCategory(catId);
        List<Event> events = eventsRepository.findByCategory(category);
        if (!events.isEmpty()) {
            throw new ConflictException("Невозможно удалить использующуюся категорию");
        }
        categoryRepository.deleteById(catId);
        log.debug("Категория {} удалена", catId);
    }

    @Override
    public CategoryDto updateCategory(Long catId, NewCategoryDto request) {
        Category oldCategory = checkCategory(catId);
        String newName = request.getName();

        if (newName != null && !oldCategory.getName().equals(newName)) {
            checkUniqueNameCategoryIgnoreCase(newName);
        }

        oldCategory.setName(newName);
        Category updatedCategory = categoryRepository.save(oldCategory);
        log.debug("Категория {} обновлена на {}", catId, updatedCategory);
        return CategoryMapper.mapToDto(updatedCategory);
    }

    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        return categoryRepository.findAll(pageRequest)
                .stream().map(CategoryMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto findCategoryById(Long catId) {
        Category category = checkCategory(catId);
        return CategoryMapper.mapToDto(category);
    }

    private Category checkCategory(Long catId) {
        return categoryRepository.findById(catId).orElseThrow(() ->
                new NotFoundException("Категории с id = " + catId + " не существует"));
    }

    private void checkUniqueNameCategoryIgnoreCase(String name) {
        if (categoryRepository.existsByNameIgnoreCase(name)) {
            throw new ConflictException(("Категория " + name + " уже существует"));
        }
    }
}
